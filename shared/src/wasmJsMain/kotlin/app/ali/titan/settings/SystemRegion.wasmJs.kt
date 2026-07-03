package app.ali.titan.settings

import kotlinx.browser.window

actual fun systemRegionCode(): String? {
    val language = window.navigator.language
    return language
        .substringAfter("-", missingDelimiterValue = "")
        .takeIf { it.isNotBlank() }
        ?.uppercase()
}