package app.ali.titan.screens.movies.domain

import app.ali.titan.screens.movies.MovieUiModel

data class MoviesPage(
    val movies: List<MovieUiModel>,
    val page: Int,
    val totalPages: Int,
)
