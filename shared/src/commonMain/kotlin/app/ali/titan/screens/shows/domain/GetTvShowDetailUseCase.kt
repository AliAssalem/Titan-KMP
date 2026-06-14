package app.ali.titan.screens.shows.domain

import app.ali.titan.screens.configuration.BackdropSize
import app.ali.titan.screens.configuration.ConfigurationStore
import app.ali.titan.screens.shows.TvShowDetailUiModel
import app.ali.titan.screens.shows.toDetailUiModel
import app.ali.titan.settings.SettingsPreferencesStore
import app.ali.titan.shared.WatchProviderUiModel
import app.ali.titan.shared.data.WatchProvider
import app.ali.titan.shared.data.WatchProviderRegion
import app.ali.titan.shared.data.WatchProvidersResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetTvShowDetailUseCase(
    private val repository: TvShowsRepository,
    private val configurationStore: ConfigurationStore,
    private val settingsPreferencesStore: SettingsPreferencesStore,
) {
    suspend operator fun invoke(
        tvShowId: Int,
        presentLabel: String,
    ): TvShowDetailUiModel =
        coroutineScope {
            val detailDeferred = async { repository.getTvShowDetail(tvShowId) }
            val providersDeferred = async { runCatching { repository.getWatchProviders(tvShowId) }.getOrNull() }
            val keywordsDeferred = async { runCatching { repository.getKeywords(tvShowId) }.getOrNull() }

            val detail = detailDeferred.await()
            val regionData = resolveRegionData(providersDeferred.await())
            val keywords =
                keywordsDeferred
                    .await()
                    ?.results
                    ?.take(MAX_KEYWORDS)
                    ?.map { it.name } ?: emptyList()

            val streamingProviders = mapProviders(regionData?.flatrate.orEmpty())
            val streamingNames = streamingProviders.map { it.name }.toSet()
            val rentBuyProviders =
                mapProviders(
                    (regionData?.rent.orEmpty() + regionData?.buy.orEmpty())
                        .distinctBy { it.providerId }
                        .filter { it.providerName !in streamingNames },
                )

            detail.toDetailUiModel(
                backdropUrl = configurationStore.backdropUrl(detail.backdropPath, BackdropSize.LARGE),
                posterUrl = configurationStore.posterUrl(detail.posterPath),
                profileUrlResolver = { configurationStore.profileUrl(it) },
                seasonPosterResolver = { configurationStore.posterUrl(it) },
                similarBackdropResolver = { configurationStore.backdropUrl(it) },
                similarPosterResolver = { configurationStore.posterUrl(it) },
                presentLabel = presentLabel,
                streamingProviders = streamingProviders,
                rentBuyProviders = rentBuyProviders,
                watchProvidersLink = regionData?.link,
                keywords = keywords,
            )
        }

    private companion object {
        const val MAX_KEYWORDS = 3
    }

    private fun resolveRegionData(response: WatchProvidersResponse?): WatchProviderRegion? {
        val results = response?.results ?: return null
        val preferred = settingsPreferencesStore.regionCode.value
        return preferred?.let { results[it] } ?: results["US"] ?: results.values.firstOrNull()
    }

    private fun mapProviders(providers: List<WatchProvider>): List<WatchProviderUiModel> =
        providers
            .sortedBy { it.displayPriority }
            .map { WatchProviderUiModel(name = it.providerName, logoUrl = configurationStore.logoUrl(it.logoPath)) }
}
