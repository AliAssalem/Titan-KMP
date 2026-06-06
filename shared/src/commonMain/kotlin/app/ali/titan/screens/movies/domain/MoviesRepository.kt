package app.ali.titan.screens.movies.domain

import app.ali.titan.screens.movies.data.Genre
import app.ali.titan.screens.movies.data.KeywordsResponse
import app.ali.titan.screens.movies.data.MovieDetail
import app.ali.titan.screens.movies.data.MoviesResponse
import app.ali.titan.shared.data.WatchProvidersResponse

interface MoviesRepository {
    suspend fun getPopularMovies(page: Int = 1): MoviesResponse

    suspend fun searchMovies(
        query: String,
        page: Int = 1,
    ): MoviesResponse

    suspend fun discoverMovies(
        genreId: Int?,
        sortBy: String,
        minRating: Float,
        page: Int = 1,
    ): MoviesResponse

    suspend fun getGenres(): List<Genre>

    suspend fun getMovieDetail(movieId: Int): MovieDetail

    suspend fun getWatchProviders(movieId: Int): WatchProvidersResponse

    suspend fun getMovieKeywords(movieId: Int): KeywordsResponse

    suspend fun getTrendingMovies(): MoviesResponse
}
