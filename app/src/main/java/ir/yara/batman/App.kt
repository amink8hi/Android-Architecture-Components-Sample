package ir.yara.batman

import dagger.hilt.android.HiltAndroidApp
import ir.yara.batman.utils.KitLog


@HiltAndroidApp()
class App : SubApp() {

    override fun onCreate() {
        super.onCreate()
        KitLog.init()
    }
}