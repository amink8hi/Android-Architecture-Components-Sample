package ir.yara.batman

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import ir.yara.batman.utils.KitLog


abstract class SubApp : MultiDexApplication() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        fixGarbageCollectorTimeOutException()
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
