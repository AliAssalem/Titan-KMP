package app.ali.titan.screens.movies.domain

import app.ali.titan.screens.movies.MovieUiMapper


class SearchMoviesUseCase(
    private val repository: MoviesRepository,
    private val mapper: MovieUiMapper,
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
    ): MoviesPage {
        val response = repository.searchMovies(query, page)
        return MoviesPage(
            movies = mapper.toUiModels(response.results),
            page = response.page,
            totalPages = response.totalPages,
        )
    }
}
