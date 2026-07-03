package app.ali.titan

import app.ali.titan.storage.DatabaseBuilderFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import kotlinx.browser.localStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module =
    module {
        single { DatabaseBuilderFactory() }
        single<Settings> {
            StorageSettings(localStorage)
        }
    }