package app.ali.titan

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import app.ali.titan.observability.initLogger
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initLogger(isDebug = true)
    initKoin {}

    ComposeViewport(document.body!!) {
        App()
    }
}