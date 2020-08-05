package ir.yara.batman

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import ir.yara.batman.utils.KitLog


@HiltAndroidApp()
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        KitLog.init()
    }

}