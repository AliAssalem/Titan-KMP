package app.ali.titan.screens.shows

import app.ali.titan.screens.configuration.ConfigurationStore
import app.ali.titan.screens.shows.data.TvShow

class TvShowUiMapper(
    private val configurationStore: ConfigurationStore,
) {
    fun toUiModels(tvShows: List<TvShow>): List<TvShowUiModel> =
        tvShows.map { tvShow ->
            tvShow.toUiModel(
                backdropUrl = configurationStore.backdropUrl(tvShow.backdropPath),
                posterUrl = configurationStore.posterUrl(tvShow.posterPath),
            )
        }
}
