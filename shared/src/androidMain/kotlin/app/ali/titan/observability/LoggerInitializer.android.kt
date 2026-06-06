package app.ali.titan.observability

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual fun initLogger(isDebug: Boolean) {
    if (isDebug) {
        Napier.base(DebugAntilog())
    }
}
