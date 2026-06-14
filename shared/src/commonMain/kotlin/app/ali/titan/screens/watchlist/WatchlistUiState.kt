package app.ali.titan.screens.watchlist

import app.ali.titan.screens.movies.MovieUiModel
import app.ali.titan.screens.shows.TvShowUiModel


sealed interface WatchlistItemUiModel {
    val key: String

    data class Movie(
        val movie: MovieUiModel,
    ) : WatchlistItemUiModel {
        override val key = "movie_${movie.id}"
    }

    data class TvShow(
        val tvShow: TvShowUiModel,
    ) : WatchlistItemUiModel {
        override val key = "tv_${tvShow.id}"
    }
}

enum class WatchlistFilter { ALL, MOVIES, TV_SHOWS }

sealed interface WatchlistUiState {
    data object Loading : WatchlistUiState

    data object Empty : WatchlistUiState

    data class Loaded(
        val filter: WatchlistFilter,
        val items: List<WatchlistItemUiModel>,
    ) : WatchlistUiState
}
