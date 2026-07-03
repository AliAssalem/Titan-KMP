package app.ali.titan

import app.ali.titan.storage.DatabaseBuilderFactory
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.prefs.Preferences

actual val platformModule: Module =
    module {
        single { DatabaseBuilderFactory() }
        single<Settings> {
            PreferencesSettings(
                Preferences.userRoot().node("titan_filter"),
            )
        }
        //single { DesktopAppReviewRequester() }
        //single<AppReviewRequester> { get<DesktopAppReviewRequester>() }
    }