package ir.yara.batman

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import ir.yara.batman.utils.KitLog


@HiltAndroidApp()
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        KitLog.init()
        MultiDex.install(this)
    }


    private fun fixGarbageCollectorTimeOutException() {
        try {
            val clazz = Class.forName("java.lang.Daemons\$FinalizerWatchdogDaemon")

            val method = clazz.superclass!!.getDeclaredMethod("stop")
            method.isAccessible = true

            val field = clazz.getDeclaredField("INSTANCE")
            field.isAccessible = true

            method.invoke(field.get(null))
        } catch (e: Throwable) {
            KitLog.e(e)
        }
    }
}