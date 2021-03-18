package network.bisq.base

import android.app.Application
import network.bisq.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("UNUSED")
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(appModule))
        }
    }
}