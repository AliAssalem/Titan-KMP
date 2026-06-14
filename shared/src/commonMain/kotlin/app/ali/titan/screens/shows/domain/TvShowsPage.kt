package app.ali.titan.screens.shows.domain

import app.ali.titan.screens.shows.TvShowUiModel


data class TvShowsPage(
    val tvShows: List<TvShowUiModel>,
    val page: Int,
    val totalPages: Int,
)
