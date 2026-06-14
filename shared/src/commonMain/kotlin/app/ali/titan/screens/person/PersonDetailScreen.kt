package app.ali.titan.screens.person

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import app.ali.titan.screens.movies.MovieUiModel
import app.ali.titan.screens.person.components.HeaderFrame
import app.ali.titan.screens.person.components.MovieFilmographyRail
import app.ali.titan.screens.person.components.PersonPhoto
import app.ali.titan.screens.person.components.ShimmerPersonDetail
import app.ali.titan.screens.person.components.TvShowFilmographyRail
import app.ali.titan.screens.shows.TvShowUiModel
import app.ali.titan.theme.ErrorContent
import app.ali.titan.theme.ExpandableText
import app.ali.titan.theme.MetadataRow
import app.ali.titan.theme.SmoovieTheme
import app.ali.titan.ui.SetStatusBarIcons
import app.ali.titan.utils.AppError
import app.ali.titan.utils.previewPersonDetailUiModel
import app.ali.titan.utils.previewPersonSummaryUiModel
import org.jetbrains.compose.resources.stringResource
import titan.shared.generated.resources.Res
import titan.shared.generated.resources.error_person_detail_failed
import titan.shared.generated.resources.media_type_movies
import titan.shared.generated.resources.media_type_tv_shows
import titan.shared.generated.resources.person_known_for_format

@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel,
    person: PersonSummaryUiModel,
    onBack: () -> Unit,
    onMovieClick: (MovieUiModel) -> Unit,
    onTvShowClick: (TvShowUiModel) -> Unit = {},
    onViewAllFilmography: (PersonFilmographyMediaType) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsState()
    PersonDetailContent(
        person = person,
        state = state,
        onBack = onBack,
        onRetry = viewModel::loadPersonDetail,
        onMovieClick = onMovieClick,
        onTvShowClick = onTvShowClick,
        onViewAllFilmography = onViewAllFilmography,
    )
}

@Composable
internal fun PersonDetailContent(
    person: PersonSummaryUiModel,
    state: PersonDetailUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onMovieClick: (MovieUiModel) -> Unit = {},
    onTvShowClick: (TvShowUiModel) -> Unit = {},
    onViewAllFilmography: (PersonFilmographyMediaType) -> Unit = {},
) {
    SetStatusBarIcons(useDarkIcons = !isSystemInDarkTheme())
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        when (state) {
            is PersonDetailUiState.Loading -> {
                ShimmerPersonDetail(onBack = onBack)
            }

            is PersonDetailUiState.Success -> {
                SuccessContent(state, onBack, onMovieClick, onTvShowClick, onViewAllFilmography)
            }

            is PersonDetailUiState.Error -> {
                SummaryHeader(person = person, onBack = onBack)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    ErrorContent(
                        error = state.error,
                        title = stringResource(Res.string.error_person_detail_failed),
                        onRetry = onRetry,
                    )
                }
            }
        }
    }
}

@Composable
private fun SuccessContent(
    state: PersonDetailUiState.Success,
    onBack: () -> Unit,
    onMovieClick: (MovieUiModel) -> Unit,
    onTvShowClick: (TvShowUiModel) -> Unit,
    onViewAllFilmography: (PersonFilmographyMediaType) -> Unit,
) {
    val moviesTitle = stringResource(Res.string.media_type_movies)
    val showsTitle = stringResource(Res.string.media_type_tv_shows)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding =
            PaddingValues(
                bottom =
                    WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding() + 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            DetailHeader(
                detail = state.personDetail,
                onBack = onBack,
            )
        }
        if (state.personDetail.movieFilmography.isNotEmpty()) {
            item {
                MovieFilmographyRail(
                    title = moviesTitle,
                    items = state.personDetail.movieFilmography,
                    onMovieClick = onMovieClick,
                    onViewAll = { onViewAllFilmography(PersonFilmographyMediaType.MOVIE) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        }
        if (state.personDetail.tvFilmography.isNotEmpty()) {
            item {
                TvShowFilmographyRail(
                    title = showsTitle,
                    items = state.personDetail.tvFilmography,
                    onTvShowClick = onTvShowClick,
                    onViewAll = { onViewAllFilmography(PersonFilmographyMediaType.TV) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun SummaryHeader(
    person: PersonSummaryUiModel,
    onBack: () -> Unit,
) {
    HeaderFrame(onBack = onBack) {
        PersonPhoto(profileUrl = person.profileUrl, name = person.name)
        Text(
            text = person.name,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
private fun DetailHeader(
    detail: PersonDetailUiModel,
    onBack: () -> Unit,
) {
    HeaderFrame(onBack = onBack) {
        PersonPhoto(profileUrl = detail.profileUrl, name = detail.name)
        Text(
            text = detail.name,
            style = MaterialTheme.typography.headlineSmall,
        )
        MetadataRow(
            detail.knownForDepartment
                .takeIf { it.isNotBlank() }
                ?.let { stringResource(Res.string.person_known_for_format, it) },
            detail.birthday.takeIf { it.isNotBlank() },
            detail.placeOfBirth.takeIf { it.isNotBlank() },
        )
        if (detail.biography.isNotBlank()) {
            ExpandableText(
                text = detail.biography,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

// region Previews

@PreviewLightDark
@Composable
private fun PersonDetailLoadingPreview() {
    SmoovieTheme {
        PersonDetailContent(
            person = previewPersonSummaryUiModel,
            state = PersonDetailUiState.Loading,
            onBack = {},
            onRetry = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun PersonDetailSuccessPreview() {
    SmoovieTheme {
        PersonDetailContent(
            person = previewPersonSummaryUiModel,
            state = PersonDetailUiState.Success(previewPersonDetailUiModel),
            onBack = {},
            onRetry = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun PersonDetailErrorPreview() {
    SmoovieTheme {
        PersonDetailContent(
            person = previewPersonSummaryUiModel,
            state = PersonDetailUiState.Error(AppError.NetworkError),
            onBack = {},
            onRetry = {},
        )
    }
}

// endregion
