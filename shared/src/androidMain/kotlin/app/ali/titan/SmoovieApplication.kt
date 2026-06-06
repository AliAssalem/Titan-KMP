package app.ali.titan

import android.app.Application
import app.ali.titan.observability.initLogger
import org.koin.android.ext.koin.androidContext

class SmoovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger(isDebug = BuildConfig.DEBUG)
        //initFirebase()
        initKoin {
            androidContext(this@SmoovieApplication)
        }
    }
    object BuildConfig {
        const val DEBUG = true
    }
//    private fun initFirebase() {
//        FirebaseApp.initializeApp(this)
//        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(appCheckProviderFactory())
//        AppCheckTokenProviderRegistry.instance = AndroidAppCheckTokenProvider()
//        CrashReportingControllerRegistry.instance = AndroidCrashReportingController()
//    }
}
