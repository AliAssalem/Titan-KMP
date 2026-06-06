package app.ali.titan.screens.movies

import app.ali.titan.screens.movies.data.Movie
import app.ali.titan.utils.toDisplayRating
import app.ali.titan.utils.toReadableDate

data class GenreUiModel(
    val id: Int,
    val name: String,
)

data class MovieUiModel(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: String,
    val backdropUrl: String?,
    val posterUrl: String? = null,
)

internal fun Movie.toUiModel(
    backdropUrl: String?,
    posterUrl: String? = null,
) = MovieUiModel(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate.toReadableDate(),
    voteAverage = voteAverage.toDisplayRating(),
    backdropUrl = backdropUrl,
    posterUrl = posterUrl,
)
