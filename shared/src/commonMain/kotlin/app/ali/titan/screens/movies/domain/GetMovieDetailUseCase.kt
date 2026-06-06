package app.ali.titan.screens.movies.domain

import app.ali.titan.screens.configuration.BackdropSize
import app.ali.titan.screens.configuration.ConfigurationStore
import app.ali.titan.screens.movies.MovieDetailUiModel
import app.ali.titan.screens.movies.toDetailUiModel
import app.ali.titan.settings.SettingsPreferencesStore
import app.ali.titan.shared.WatchProviderUiModel
import app.ali.titan.shared.data.WatchProvider
import app.ali.titan.shared.data.WatchProviderRegion
import app.ali.titan.shared.data.WatchProvidersResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetMovieDetailUseCase(
    private val repository: MoviesRepository,
    private val configurationStore: ConfigurationStore,
    private val settingsPreferencesStore: SettingsPreferencesStore,
) {
    suspend operator fun invoke(movieId: Int): MovieDetailUiModel =
        coroutineScope {
            val detailDeferred = async { repository.getMovieDetail(movieId) }
            val providersDeferred = async { runCatching { repository.getWatchProviders(movieId) }.getOrNull() }
            val keywordsDeferred = async { runCatching { repository.getMovieKeywords(movieId) }.getOrNull() }

            val detail = detailDeferred.await()
            val regionData = resolveRegionData(providersDeferred.await())
            val keywords =
                keywordsDeferred
                    .await()
                    ?.keywords
                    ?.take(MAX_KEYWORDS)
                    ?.map { it.name } ?: emptyList()

            val streamingProviders = mapProviders(regionData?.flatrate.orEmpty())
            val streamingIds = streamingProviders.map { it.name }.toSet()
            val rentBuyProviders =
                mapProviders(
                    (regionData?.rent.orEmpty() + regionData?.buy.orEmpty())
                        .distinctBy { it.providerId }
                        .filter { it.providerName !in streamingIds },
                )

            detail.toDetailUiModel  (
                backdropUrl = configurationStore.backdropUrl(detail.backdropPath, BackdropSize.LARGE),
                posterUrl = configurationStore.posterUrl(detail.posterPath),
                profileUrlResolver = { configurationStore.profileUrl(it) },
                movieBackdropUrlResolver = { configurationStore.backdropUrl(it) },
                moviePosterUrlResolver = { configurationStore.posterUrl(it) },
                streamingProviders = streamingProviders,
                rentBuyProviders = rentBuyProviders,
                watchProvidersLink = regionData?.link,
                keywords = keywords,
            )
        }

    private companion object {
        const val MAX_KEYWORDS = 3
    }

    private fun resolveRegionData(response: WatchProvidersResponse?): WatchProviderRegion ? {
        val results = response?.results ?: return null
        val preferred = settingsPreferencesStore.regionCode.value
        return preferred?.let { results[it] } ?: results["US"] ?: results.values.firstOrNull()
    }

    private fun mapProviders(providers: List<WatchProvider>): List<WatchProviderUiModel> =
        providers
            .sortedBy { it.displayPriority }
            .map { WatchProviderUiModel(name = it.providerName, logoUrl = configurationStore.logoUrl(it.logoPath)) }
}
