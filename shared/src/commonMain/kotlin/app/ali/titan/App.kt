package app.ali.titan

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import app.ali.titan.screens.movies.MovieDetailScreen
import app.ali.titan.screens.movies.MovieDetailViewModel
import app.ali.titan.screens.movies.MoviesScreen
import app.ali.titan.screens.movies.MoviesViewModel
import app.ali.titan.screens.person.PersonDetailScreen
import app.ali.titan.screens.person.PersonDetailViewModel
import app.ali.titan.screens.person.PersonFilmographyMediaType
import app.ali.titan.screens.person.PersonFilmographyScreen
import app.ali.titan.screens.person.PersonFilmographyViewModel
import app.ali.titan.screens.shows.SeasonDetailScreen
import app.ali.titan.screens.shows.SeasonDetailViewModel
import app.ali.titan.screens.shows.ShowsScreen
import app.ali.titan.screens.shows.ShowsViewModel
import app.ali.titan.screens.shows.TvShowDetailScreen
import app.ali.titan.screens.shows.TvShowDetailViewModel
import app.ali.titan.screens.watchlist.WatchlistScreen
import app.ali.titan.screens.watchlist.WatchlistViewModel
import app.ali.titan.settings.CrashReportingConsentViewModel
import app.ali.titan.settings.SettingsScreen
import app.ali.titan.settings.SettingsViewModel
import app.ali.titan.settings.components.CrashReportingConsentSheet
import app.ali.titan.theme.TitanTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import titan.shared.generated.resources.Res
import titan.shared.generated.resources.media_type_movies
import titan.shared.generated.resources.media_type_tv_shows
import titan.shared.generated.resources.settings_title
import titan.shared.generated.resources.tv_show_year_range_present
import titan.shared.generated.resources.watchlist_title

private const val TRANSITION_DURATION_MS = 500
private val TransitionEasing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    TitanTheme {
        Surface {
            val navController = rememberNavController()
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntry?.destination
            val crashReportingConsentViewModel: CrashReportingConsentViewModel = koinViewModel()
            val isConsentSheetVisible by crashReportingConsentViewModel.isVisible.collectAsState()

            Scaffold(
                contentWindowInsets = WindowInsets(0),
                bottomBar = {
                    if (currentDestination?.isTopLevelTab() == true) {
                        AppBottomBar(
                            currentDestination = currentDestination,
                            onTabSelected = navController::navigateToTab,
                        )
                    }
                },
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = MoviesRoute,
                    modifier = Modifier.padding(innerPadding),
                    enterTransition = {
                        slideInHorizontally(tween(TRANSITION_DURATION_MS, easing = TransitionEasing)) { it }
                    },
                    exitTransition = {
                        slideOutHorizontally(tween(TRANSITION_DURATION_MS, easing = TransitionEasing)) { -it / 3 }
                    },
                    popEnterTransition = {
                        slideInHorizontally(tween(TRANSITION_DURATION_MS, easing = TransitionEasing)) { -it / 3 }
                    },
                    popExitTransition = {
                        slideOutHorizontally(tween(TRANSITION_DURATION_MS, easing = TransitionEasing)) { it }
                    },
                ) {
                    composable<MoviesRoute>(
                        enterTransition = { if (initialState.destination.isTopLevelTab()) EnterTransition.None else null },
                        exitTransition = { if (targetState.destination.isTopLevelTab()) ExitTransition.None else null },
                        popEnterTransition = { if (initialState.destination.isTopLevelTab()) EnterTransition.None else null },
                        popExitTransition = { if (targetState.destination.isTopLevelTab()) ExitTransition.None else null },
                    ) {
                        val viewModel: MoviesViewModel = koinViewModel()
                        MoviesScreen(
                            viewModel = viewModel,
                            onMovieClick = { movie -> navController.navigate(movie.toRoute()) },
                        )
                    }

                    composable<ShowsRoute>(
                        enterTransition = { if (initialState.destination.isTopLevelTab()) EnterTransition.None else null },
                        exitTransition = { if (targetState.destination.isTopLevelTab()) ExitTransition.None else null },
                        popEnterTransition = { if (initialState.destination.isTopLevelTab()) EnterTransition.None else null },
                        popExitTransition = { if (targetState.destination.isTopLevelTab()) ExitTransition.None else null },
                    ) {
                        val viewModel: ShowsViewModel = koinViewModel()
                        ShowsScreen(
                            viewModel = viewModel,
                            onTvShowClick = { show -> navController.navigate(show.toRoute()) },
                        )
                    }

                    composable<WatchlistRoute>(
                        enterTransition = { if (initialState.destination.isTopLevelTab()) EnterTransition.None else null },
                        exitTransition = { if (targetState.destination.isTopLevelTab()) ExitTransition.None else null },
                        popEnterTransition = { if (initialState.destination.isTopLevelTab()) EnterTransition.None else null },
                        popExitTransition = { if (targetState.destination.isTopLevelTab()) ExitTransition.None else null },
                    ) {
                        val viewModel: WatchlistViewModel = koinViewModel()
                        WatchlistScreen(
                            viewModel = viewModel,
                            onMovieClick = { movie -> navController.navigate(movie.toRoute()) },
                            onTvShowClick = { show -> navController.navigate(show.toRoute()) },
                        )
                    }

                    composable<SettingsRoute>(
                        enterTransition = { if (initialState.destination.isTopLevelTab()) EnterTransition.None else null },
                        exitTransition = { if (targetState.destination.isTopLevelTab()) ExitTransition.None else null },
                        popEnterTransition = { if (initialState.destination.isTopLevelTab()) EnterTransition.None else null },
                        popExitTransition = { if (targetState.destination.isTopLevelTab()) ExitTransition.None else null },
                    ) {
                        val viewModel: SettingsViewModel = koinViewModel()
                        SettingsScreen(viewModel = viewModel)
                    };

                    composable<MovieDetailRoute> { entry ->
                        val route: MovieDetailRoute = entry.toRoute()
                        val viewModel: MovieDetailViewModel =
                            koinViewModel(
                                key = route.id.toString(),
                                parameters = { parametersOf(route.id) },
                            )
                        MovieDetailScreen(
                            viewModel = viewModel,
                            movie = route.toUiModel(),
                            onBack = { navController.navigateUp() },
                            onMovieClick = { movie -> navController.navigate(movie.toRoute()) },
                            onPersonClick = { person -> navController.navigate(person.toRoute()) },
                        )
                    }

                    composable<PersonDetailRoute> { entry ->
                        val route: PersonDetailRoute = entry.toRoute()
                        val viewModel: PersonDetailViewModel =
                            koinViewModel(
                                key = "person_${route.id}",
                                parameters = { parametersOf(route.id) },
                            )
                        PersonDetailScreen(
                            viewModel = viewModel,
                            person = route.toUiModel(),
                            onBack = { navController.navigateUp() },
                            onMovieClick = { movie -> navController.navigate(movie.toRoute()) },
                            onTvShowClick = { show -> navController.navigate(show.toRoute()) },
                            onViewAllFilmography = { mediaType ->
                                navController.navigate(
                                    PersonFilmographyRoute(
                                        personId = route.id,
                                        personName = route.name,
                                        mediaType = mediaType.name,
                                    ),
                                )
                            },
                        )
                    }

                    composable<PersonFilmographyRoute> { entry ->
                        val route: PersonFilmographyRoute = entry.toRoute()
                        val mediaType = PersonFilmographyMediaType.valueOf(route.mediaType)
                        val viewModel: PersonFilmographyViewModel =
                            koinViewModel(
                                key = "person_filmography_${route.personId}_${route.mediaType}",
                                parameters = { parametersOf(route.personId) },
                            )
                        PersonFilmographyScreen(
                            viewModel = viewModel,
                            personName = route.personName,
                            mediaType = mediaType,
                            onBack = { navController.navigateUp() },
                            onMovieClick = { movie -> navController.navigate(movie.toRoute()) },
                            onTvShowClick = { show -> navController.navigate(show.toRoute()) },
                        )
                    }

                    composable<TvShowDetailRoute> { entry ->
                        val route: TvShowDetailRoute = entry.toRoute()
                        val presentLabel = stringResource(Res.string.tv_show_year_range_present)
                        val viewModel: TvShowDetailViewModel =
                            koinViewModel(
                                key = "tv_${route.id}",
                                parameters = { parametersOf(route.id, presentLabel) },
                            )
                        TvShowDetailScreen(
                            viewModel = viewModel,
                            tvShow = route.toUiModel(),
                            onBack = { navController.navigateUp() },
                            onTvShowClick = { show -> navController.navigate(show.toRoute()) },
                            onPersonClick = { person -> navController.navigate(person.toRoute()) },
                            onSeasonClick = { season ->
                                navController.navigate(
                                    TvSeasonDetailRoute(
                                        tvShowId = route.id,
                                        seasonNumber = season.seasonNumber,
                                        seasonName = season.name,
                                    ),
                                )
                            },
                        )
                    }

                    composable<TvSeasonDetailRoute> { entry ->
                        val route: TvSeasonDetailRoute = entry.toRoute()
                        val viewModel: SeasonDetailViewModel =
                            koinViewModel(
                                key = "season_${route.tvShowId}_${route.seasonNumber}",
                                parameters = { parametersOf(route.tvShowId, route.seasonNumber) },
                            )
                        SeasonDetailScreen(
                            viewModel = viewModel,
                            seasonName = route.seasonName,
                            onBack = { navController.navigateUp() },
                        )
                    }
                }
            }

            if (isConsentSheetVisible) {
                CrashReportingConsentSheet(
                    onEnable = crashReportingConsentViewModel::onEnable,
                    onDecline = crashReportingConsentViewModel::onDecline,
                )
            }
        }
    }
}

@Composable
private fun AppBottomBar(
    currentDestination: NavDestination,
    onTabSelected: (Any) -> Unit,
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentDestination.hasRoute<MoviesRoute>(),
            onClick = { onTabSelected(MoviesRoute) },
            icon = { Icon(Icons.Default.Movie, contentDescription = null) },
            label = { Text(stringResource(Res.string.media_type_movies)) },
        )
        NavigationBarItem(
            selected = currentDestination.hasRoute<ShowsRoute>(),
            onClick = { onTabSelected(ShowsRoute) },
            icon = { Icon(Icons.Default.Tv, contentDescription = null) },
            label = { Text(stringResource(Res.string.media_type_tv_shows)) },
        )
        NavigationBarItem(
            selected = currentDestination.hasRoute<WatchlistRoute>(),
            onClick = { onTabSelected(WatchlistRoute) },
            icon = { Icon(Icons.Default.BookmarkBorder, contentDescription = null) },
            label = { Text(stringResource(Res.string.watchlist_title)) },
        )
        NavigationBarItem(
            selected = currentDestination.hasRoute<SettingsRoute>(),
            onClick = { onTabSelected(SettingsRoute) },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text(stringResource(Res.string.settings_title)) },
        )
    }
}

private fun NavDestination.isTopLevelTab(): Boolean =
    hasRoute<MoviesRoute>() ||
            hasRoute<ShowsRoute>() ||
            hasRoute<WatchlistRoute>() ||
            hasRoute<SettingsRoute>()

private fun NavHostController.navigateToTab(route: Any) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
