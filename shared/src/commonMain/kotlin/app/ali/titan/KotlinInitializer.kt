package app.ali.titan

import app.ali.titan.observability.Logger
import app.ali.titan.observability.NapierKtorLogger
import app.ali.titan.observability.NapierLogger
import app.ali.titan.screens.configuration.ConfigurationRepository
import app.ali.titan.screens.configuration.ConfigurationRepositoryImpl
import app.ali.titan.screens.configuration.ConfigurationStore
import app.ali.titan.screens.configuration.LoadConfigurationUseCase
import app.ali.titan.screens.filter.FilterPreferencesStore
import app.ali.titan.screens.filter.FilterPreferencesStoreImpl
import app.ali.titan.screens.movies.MovieUiMapper
import app.ali.titan.screens.movies.MoviesViewModel
import app.ali.titan.screens.movies.data.MoviesRepositoryImpl
import app.ali.titan.screens.movies.domain.DiscoverMoviesUseCase
import app.ali.titan.screens.movies.domain.GetGenresUseCase
import app.ali.titan.screens.movies.domain.GetMovieDetailUseCase
import app.ali.titan.screens.movies.domain.GetPopularMoviesUseCase
import app.ali.titan.screens.movies.domain.GetTrendingMoviesUseCase
import app.ali.titan.screens.movies.domain.MoviesRepository
import app.ali.titan.screens.movies.domain.SearchMoviesUseCase
import app.ali.titan.settings.SettingsPreferencesStore
import app.ali.titan.settings.SettingsPreferencesStoreImpl
import dev.odaridavid.smoovie.observability.setCrashReportingEnabled
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun initKoin(setup: KoinApplication.() -> Unit = {}) {
    val koinApp =
        startKoin {
            setup()
            modules(appModule, platformModule)
        }
    val settingsPreferencesStore = koinApp.koin.get<SettingsPreferencesStore>()
    setCrashReportingEnabled(settingsPreferencesStore.crashReportingEnabled.value)
}

private val appModule =
    module {
        single<Logger> { NapierLogger() }
        single {
            HttpClient {
                defaultRequest {
                    header(
                        "Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMGM1ZjA5ZjViY2NkNzQ5NzkxNTk2MTAwZDgwMTFiMCIsIm5iZiI6MTY2NDAwOTg1MS44MjksInN1YiI6IjYzMmVjNjdiMmEyMTBjMDA3YWU0YzU4ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.edRtw32He7dS4aBNyVyblLygHQfDCHK7Yi85k7UBrdU"
                    )
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            coerceInputValues = true
                        },
                    )
                }
                install(Logging) {
                    logger = NapierKtorLogger(get())
                    level = LogLevel.HEADERS
                }
                //install(AppCheckHeader)
            }
        }
        single { ConfigurationStore() }
        single<ConfigurationRepository> { ConfigurationRepositoryImpl(get()) }
        single<SettingsPreferencesStore> { SettingsPreferencesStoreImpl(get()) }
        single<MoviesRepository> { MoviesRepositoryImpl(get(), get()) }

        single { MovieUiMapper(get()) }
        single { LoadConfigurationUseCase(get(), get()) }
        single { GetPopularMoviesUseCase(get(), get()) }
        single { SearchMoviesUseCase(get(), get()) }
        single { DiscoverMoviesUseCase(get(), get()) }
        single { GetGenresUseCase(get()) }
        single { GetMovieDetailUseCase(get(), get(), get()) }
        single { GetTrendingMoviesUseCase(get(), get()) }

        single<FilterPreferencesStore> { FilterPreferencesStoreImpl(get()) }


        viewModel {
            MoviesViewModel(
                getPopularMovies = get(),
                getTrendingMovies = get(),
                searchMovies = get(),
                discoverMovies = get(),
                getGenres = get(),
                loadConfiguration = get(),
                filterPreferencesStore = get(),
                settingsPreferencesStore = get(),
            )
        }
    }