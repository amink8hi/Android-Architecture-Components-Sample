package ir.yara.batman

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import ir.yara.batman.utils.KitLog
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp()
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        KitLog.init()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }

}