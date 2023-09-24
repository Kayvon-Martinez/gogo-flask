package app.kyushu.aynime

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AynimeApplication : Application() {
    @Override
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}