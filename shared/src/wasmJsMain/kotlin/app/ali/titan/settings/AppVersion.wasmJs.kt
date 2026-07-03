package app.ali.titan.settings

import app.ali.titan.BuildConfig


actual fun appVersionInfo(): AppVersion =
    AppVersion(
        name = BuildConfig.VERSION_NAME,
        code = BuildConfig.VERSION_CODE.toString(),
    )
