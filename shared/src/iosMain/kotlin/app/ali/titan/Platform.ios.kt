package app.ali.titan

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIDevice

actual val platformModule: Module =
    module {
        //single { DatabaseBuilderFactory() }
        single<Settings> { NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults) }
       // single<AppReviewRequester> { IosAppReviewRequester() }
    }
