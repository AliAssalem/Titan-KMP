package app.ali.titan.settings

data class SettingsUiState(
    val selectedRegion: Region? = null,
    val regions: List<Region> = SUPPORTED_REGIONS,
    val crashReportingEnabled: Boolean = false,
)
