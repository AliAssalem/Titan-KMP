package app.ali.titan

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.ali.titan.observability.initLogger

fun main() {
    initLogger(isDebug = true)
    initKoin {

    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Titan",
        ) {
            App()
        }
    }
}