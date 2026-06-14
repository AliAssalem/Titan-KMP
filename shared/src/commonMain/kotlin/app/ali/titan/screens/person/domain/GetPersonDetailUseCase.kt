package app.ali.titan.screens.person.domain

import app.ali.titan.screens.configuration.ConfigurationStore
import app.ali.titan.screens.configuration.ProfileSize
import app.ali.titan.screens.person.PersonDetailUiModel
import app.ali.titan.screens.person.toDetailUiModel


class GetPersonDetailUseCase(
    private val repository: PersonRepository,
    private val configurationStore: ConfigurationStore,
) {
    suspend operator fun invoke(personId: Int): PersonDetailUiModel =
        repository.getPersonDetail(personId).let { person ->
            person.toDetailUiModel(
                profileUrl = configurationStore.profileUrl(person.profilePath, ProfileSize.LARGE),
                moviePosterUrlResolver = { configurationStore.posterUrl(it) },
                movieBackdropUrlResolver = { configurationStore.backdropUrl(it) },
                tvPosterUrlResolver = { configurationStore.posterUrl(it) },
                tvBackdropUrlResolver = { configurationStore.backdropUrl(it) },
            )
        }
}
