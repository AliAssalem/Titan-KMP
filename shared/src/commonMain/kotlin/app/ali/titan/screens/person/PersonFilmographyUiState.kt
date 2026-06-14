package app.ali.titan.screens.person

import app.ali.titan.utils.AppError


sealed interface PersonFilmographyUiState {
    data object Loading : PersonFilmographyUiState

    data class Success(
        val personDetail: PersonDetailUiModel,
    ) : PersonFilmographyUiState

    data class Error(
        val error: AppError,
    ) : PersonFilmographyUiState
}
