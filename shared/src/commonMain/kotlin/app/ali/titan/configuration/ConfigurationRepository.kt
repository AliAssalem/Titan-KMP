package app.ali.titan.screens.configuration

interface ConfigurationRepository {
    suspend fun getImagesConfiguration(): ImagesConfiguration
}
