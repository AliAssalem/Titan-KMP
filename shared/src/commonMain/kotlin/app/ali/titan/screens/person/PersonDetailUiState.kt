package app.ali.titan.screens.person

import app.ali.titan.utils.AppError


sealed interface PersonDetailUiState {
    data object Loading : PersonDetailUiState

    data class Success(
        val personDetail: PersonDetailUiModel,
    ) : PersonDetailUiState

    data class Error(
        val error: AppError,
    ) : PersonDetailUiState
}
