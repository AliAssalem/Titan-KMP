package app.ali.titan.screens.movies.domain

import app.ali.titan.screens.movies.MovieUiMapper
import app.ali.titan.screens.movies.MovieUiModel

class GetTrendingMoviesUseCase(
    private val repository: MoviesRepository,
    private val mapper: MovieUiMapper,
) {
    suspend operator fun invoke(): List<MovieUiModel> = mapper.toUiModels(repository.getTrendingMovies().results)
}
