package app.ali.titan.screens.shows.domain

import app.ali.titan.screens.shows.data.SeasonDetail
import app.ali.titan.screens.shows.data.TvGenre
import app.ali.titan.screens.shows.data.TvKeywordsResponse
import app.ali.titan.screens.shows.data.TvShowDetail
import app.ali.titan.screens.shows.data.TvShowsResponse
import app.ali.titan.shared.data.WatchProvidersResponse

interface TvShowsRepository {
    suspend fun getPopularTvShows(page: Int = 1): TvShowsResponse

    suspend fun searchTvShows(
        query: String,
        page: Int = 1,
    ): TvShowsResponse

    suspend fun discoverTvShows(
        genreId: Int?,
        sortBy: String,
        minRating: Float,
        page: Int = 1,
    ): TvShowsResponse

    suspend fun getGenres(): List<TvGenre>

    suspend fun getTvShowDetail(tvShowId: Int): TvShowDetail

    suspend fun getSeasonDetail(
        tvShowId: Int,
        seasonNumber: Int,
    ): SeasonDetail

    suspend fun getWatchProviders(tvShowId: Int): WatchProvidersResponse

    suspend fun getKeywords(tvShowId: Int): TvKeywordsResponse
}
