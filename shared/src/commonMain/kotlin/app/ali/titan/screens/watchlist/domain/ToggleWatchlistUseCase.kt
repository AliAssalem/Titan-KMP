package app.ali.titan.screens.watchlist.domain

import app.ali.titan.screens.movies.MovieUiModel
import app.ali.titan.screens.shows.TvShowUiModel

class ToggleWatchlistUseCase(
    private val repository: WatchlistRepository,
) {
    suspend operator fun invoke(movie: MovieUiModel) {
        repository.toggle(movie.toWatchlistEntry())
    }

    suspend operator fun invoke(tvShow: TvShowUiModel) {
        repository.toggle(tvShow.toWatchlistEntry())
    }
}
