package app.ali.titan.screens.movies.domain

import app.ali.titan.screens.filter.MovieFilterPreferences
import app.ali.titan.screens.movies.MovieUiMapper


class DiscoverMoviesUseCase(
    private val repository: MoviesRepository,
    private val mapper: MovieUiMapper ,
) {
    suspend operator fun invoke(
        filter: MovieFilterPreferences,
        page: Int = 1,
    ): MoviesPage {
        val response =
            repository.discoverMovies(
                genreId = filter.selectedGenreId,
                sortBy = filter.sortBy.apiValue,
                minRating = filter.minRating,
                page = page,
            )
        return MoviesPage(
            movies = mapper.toUiModels(response.results),
            page = response.page,
            totalPages = response.totalPages,
        )
    }
}
