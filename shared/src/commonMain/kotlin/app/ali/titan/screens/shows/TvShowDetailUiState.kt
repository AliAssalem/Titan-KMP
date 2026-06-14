package app.ali.titan.screens.shows

import app.ali.titan.utils.AppError


sealed interface TvShowDetailUiState {
    data object Loading : TvShowDetailUiState

    data class Success(
        val tvShowDetail: TvShowDetailUiModel,
    ) : TvShowDetailUiState

    data class Error(
        val error: AppError,
    ) : TvShowDetailUiState
}
