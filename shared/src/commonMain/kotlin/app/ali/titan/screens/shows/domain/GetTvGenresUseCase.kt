package app.ali.titan.screens.shows.domain

import app.ali.titan.screens.shows.TvGenreUiModel

class GetTvGenresUseCase(
    private val repository: TvShowsRepository,
) {
    suspend operator fun invoke(): List<TvGenreUiModel> = repository.getGenres().map { TvGenreUiModel(it.id, it.name) }
}
