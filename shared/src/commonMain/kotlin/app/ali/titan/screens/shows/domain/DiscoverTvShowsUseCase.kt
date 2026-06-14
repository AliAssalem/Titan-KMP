package app.ali.titan.screens.shows.domain

import app.ali.titan.screens.filter.TvFilterPreferences
import app.ali.titan.screens.shows.TvShowUiMapper

class DiscoverTvShowsUseCase(
    private val repository: TvShowsRepository,
    private val mapper: TvShowUiMapper,
) {
    suspend operator fun invoke(
        filter: TvFilterPreferences,
        page: Int = 1,
    ): TvShowsPage {
        val response =
            repository.discoverTvShows(
                genreId = filter.selectedGenreId,
                sortBy = filter.sortBy.apiValue,
                minRating = filter.minRating,
                page = page,
            )
        return TvShowsPage(
            tvShows = mapper.toUiModels(response.results),
            page = response.page,
            totalPages = response.totalPages,
        )
    }
}
