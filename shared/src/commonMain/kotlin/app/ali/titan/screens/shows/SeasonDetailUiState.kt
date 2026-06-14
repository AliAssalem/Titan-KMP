package app.ali.titan.screens.shows

import app.ali.titan.utils.AppError


sealed interface SeasonDetailUiState {
    data object Loading : SeasonDetailUiState

    data class Success(
        val seasonDetail: SeasonDetailUiModel,
    ) : SeasonDetailUiState

    data class Error(
        val error: AppError,
    ) : SeasonDetailUiState
}
