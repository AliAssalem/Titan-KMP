package app.ali.titan

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module =
    module {
        //single { DatabaseBuilderFactory(androidContext()) }
        single<Settings> {
            SharedPreferencesSettings(
                androidContext().getSharedPreferences("smoovie_filter", Context.MODE_PRIVATE),
            )
        }
        //single { AndroidAppReviewRequester() }
        //single<AppReviewRequester> { get<AndroidAppReviewRequester>() }
    }