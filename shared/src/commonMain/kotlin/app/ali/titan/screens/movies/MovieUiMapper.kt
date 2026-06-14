package app.ali.titan.screens.movies

import app.ali.titan.screens.movies.data.Movie
import app.ali.titan.screens.configuration.ConfigurationStore

class MovieUiMapper(
    private val configurationStore: ConfigurationStore,
) {
    fun toUiModels(movies: List<Movie>): List<MovieUiModel> =
        movies.map { movie ->
            movie.toUiModel(
                backdropUrl = configurationStore.backdropUrl(movie.backdropPath),
                posterUrl = configurationStore.posterUrl(movie.posterPath),
            )
        }
}