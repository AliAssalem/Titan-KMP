package app.ali.titan.screens.movies.domain

import app.ali.titan.screens.movies.GenreUiModel


class GetGenresUseCase(
    private val repository: MoviesRepository,
) {
    suspend operator fun invoke(): List<GenreUiModel> = repository.getGenres().map { GenreUiModel(it.id, it.name) }
}
