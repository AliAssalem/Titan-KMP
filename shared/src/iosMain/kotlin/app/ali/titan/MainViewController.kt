package app.ali.titan

import androidx.compose.ui.window.ComposeUIViewController
import app.ali.titan.observability.initLogger
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
@Suppress("ktlint:standard:function-naming")
fun MainViewController() =
    ComposeUIViewController {
        initLogger(isDebug = Platform.isDebugBinary)
        initKoin()
        App()
    }