package app.ali.titan.screens.movies.domain

import app.ali.titan.screens.movies.MovieUiMapper
import app.ali.titan.screens.movies.domain.MoviesRepository

class GetPopularMoviesUseCase(
    private val repository: MoviesRepository,
    private val mapper: MovieUiMapper,
) {
    suspend operator fun invoke(page: Int = 1): MoviesPage {
        val response = repository.getPopularMovies(page)
        return MoviesPage(
            movies = mapper.toUiModels(response.results),
            page = response.page,
            totalPages = response.totalPages,
        )
    }
}
