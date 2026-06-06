package app.ali.titan.screens.configuration


class LoadConfigurationUseCase(
    private val repository: ConfigurationRepository,
    private val store: ConfigurationStore,
) {
    suspend operator fun invoke() {
        store.save(repository.getImagesConfiguration())
    }
}
