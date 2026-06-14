package app.ali.titan.screens.movies

import app.ali.titan.utils.AppError

sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState

    data class Success(
        val movieDetail: MovieDetailUiModel,
    ) : MovieDetailUiState

    data class Error(
        val error: AppError,
    ) : MovieDetailUiState
}
