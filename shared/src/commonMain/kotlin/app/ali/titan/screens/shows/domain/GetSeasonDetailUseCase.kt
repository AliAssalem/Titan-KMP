package app.ali.titan.screens.shows.domain

import app.ali.titan.screens.configuration.BackdropSize
import app.ali.titan.screens.configuration.ConfigurationStore
import app.ali.titan.screens.shows.SeasonDetailUiModel
import app.ali.titan.screens.shows.toUiModel

class GetSeasonDetailUseCase(
    private val repository: TvShowsRepository,
    private val configurationStore: ConfigurationStore,
) {
    suspend operator fun invoke(
        tvShowId: Int,
        seasonNumber: Int,
    ): SeasonDetailUiModel =
        repository.getSeasonDetail(tvShowId, seasonNumber).let { detail ->
            detail.toUiModel(
                posterUrl = configurationStore.posterUrl(detail.posterPath),
                stillUrlResolver = { configurationStore.backdropUrl(it, BackdropSize.SMALL) },
            )
        }
}
