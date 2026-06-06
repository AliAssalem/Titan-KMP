package app.ali.titan.screens.movies

import app.ali.titan.screens.filter.MovieFilterPreferences
import app.ali.titan.utils.AppError


data class MoviesScreenState(
    val uiState: MoviesUiState = MoviesUiState.Loading,
    val searchQuery: String = "",
    val genres: List<GenreUiModel> = emptyList(),
    val filterPreferences: MovieFilterPreferences = MovieFilterPreferences(),
    val featuredMovies: List<MovieUiModel> = emptyList(),
)

sealed interface MoviesUiState {
    data object Loading : MoviesUiState

    data class Success(
        val movies: List<MovieUiModel>,
        val isLoadingMore: Boolean = false,
        val hasMorePages: Boolean = false,
    ) : MoviesUiState

    data object Empty : MoviesUiState

    data class Error(
        val error: AppError,
    ) : MoviesUiState
}
