package ir.yara.batman.ui.base

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.MutableLiveData
import androidx.transition.Fade
import androidx.transition.Slide
import com.google.android.material.snackbar.Snackbar
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.yara.batman.ui.view.helper.HalfInterpolator
import ir.yara.batman.utils.KitLog
import ir.yara.batman.utils.extensions.default
import java.util.*


open class BaseActivity : AppCompatActivity() {

    var kitBackStack = MutableLiveData<MutableList<String>>().default(mutableListOf())
    val LOG_TAG = javaClass.simpleName

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE")
        super.onSaveInstanceState(outState)
    }

    fun disableScreenShot() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    fun enableScreenShot() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    fun startActivity(aClass: Class<*>) {
        val intent = Intent(this, aClass)
        startActivity(intent)
    }

    fun replaceFragment(fragment: Fragment, @StringRes name: Int, viewArray: List<Int>?): Boolean? {
        val tag = fragment.javaClass.canonicalName ?: fragment.javaClass.name
        val isFragmentFind = supportFragmentManager.findFragmentByTag(tag) != null
        if (!fragment.isStateSaved and isFragmentFind.not()) {
            viewArray?.let {
                val transition = getTransition(it)
                transition?.let {
                    fragment.enterTransition = transition.first
                    fragment.returnTransition = transition.second
                }
            }
            val tz: FragmentTransaction = supportFragmentManager.beginTransaction()
            tz.replace(android.R.id.content, fragment)
            return commitHandler(tz, baseContext.resources.getString(name))
        }
        return null
    }

    fun replaceFragmentNullBackStack(
        fragment: Fragment,
        @StringRes name: Int,
        viewArray: List<Int>?
    ): Boolean? {
        val tag = fragment.javaClass.canonicalName ?: fragment.javaClass.name
        val isFragmentFind = supportFragmentManager.findFragmentByTag(tag) != null
        if (!fragment.isStateSaved and isFragmentFind.not()) {
            val pageName = baseContext.resources.getString(name)
            kitBackStack.value!!.add(pageName)
            viewArray?.let {
                val transition = getTransition(it)
                transition?.let {
                    fragment.enterTransition = transition.first
                    fragment.returnTransition = transition.second
                }
            }
            val tz: FragmentTransaction = supportFragmentManager.beginTransaction()
            tz.replace(android.R.id.content, fragment)
            tz.addToBackStack(null)
            return commitHandler(tz, pageName)
        }
        return null
    }

    fun removeAllReplaceFragment(
        fragment: Fragment,
        @StringRes name: Int,
        viewArray: List<Int>?
    ): Boolean? {

        val tag = fragment.javaClass.canonicalName ?: fragment.javaClass.name
        val isFragmentFind = supportFragmentManager.findFragmentByTag(tag) != null
        if (fragment.isStateSaved or fragment.isAdded and isFragmentFind) {
            return null
        }
        val pageName = baseContext.resources.getString(name)
        kitBackStack.value = mutableListOf()
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        viewArray?.let {
            val transition = getTransition(it)
            transition?.let {
                fragment.enterTransition = transition.first
                fragment.returnTransition = transition.second
            }
        }
        val tz: FragmentTransaction = supportFragmentManager.beginTransaction()
        tz.replace(android.R.id.content, fragment)

        return commitHandler(tz, pageName)
    }

    fun addFragmentNullBackStack(
        fragment: Fragment,
        @StringRes name: Int,
        viewArray: List<Int>?
    ): Boolean? {
        executePendingTransaction()
        val tag = fragment.javaClass.canonicalName ?: fragment.javaClass.name
        val isFragmentFind = supportFragmentManager.findFragmentByTag(tag) != null
        if (fragment.isStateSaved or fragment.isAdded or isFragmentFind) {
            return null
        }
        val pageName = baseContext.resources.getString(name)
        kitBackStack.value!!.add(pageName)
        viewArray?.let {
            val transition = getTransition(it)
            transition?.let {
                fragment.enterTransition = transition.first
                fragment.returnTransition = transition.second
            }
        }
        val tz: FragmentTransaction = supportFragmentManager.beginTransaction()
        tz.add(android.R.id.content, fragment)
        tz.addToBackStack(null)
        return commitHandler(tz, pageName)
    }

    fun addFragmentNullBackStack(
        fragment: Fragment,
        stringName: String,
        viewArray: List<Int>?
    ): Boolean? {
        executePendingTransaction()
        val tag = fragment.javaClass.canonicalName ?: fragment.javaClass.name
        val isFragmentFind = supportFragmentManager.findFragmentByTag(tag) != null
        if (fragment.isStateSaved or fragment.isAdded or isFragmentFind) {
            return null
        }
        kitBackStack.value!!.add(stringName)
        viewArray?.let {
            val transition = getTransition(it)
            transition?.let {
                fragment.enterTransition = transition.first
                fragment.returnTransition = transition.second
            }
        }
        val tz: FragmentTransaction = supportFragmentManager.beginTransaction()
        tz.add(android.R.id.content, fragment)
        tz.addToBackStack(null)
        return commitHandler(tz, stringName)
    }

    fun removeAddFragmentNullBackStack(
        fragment: Fragment,
        @StringRes name: Int,
        viewArray: List<Int>?
    ): Boolean? {
        executePendingTransaction()
        val tag = fragment.javaClass.canonicalName ?: fragment.javaClass.name
        val isFragmentFind = supportFragmentManager.findFragmentByTag(tag) != null
        if (fragment.isStateSaved or fragment.isAdded and isFragmentFind) {
            return null
        }
        val pageName = baseContext.resources.getString(name)
        kitBackStack.value!!.removeAt(kitBackStack.value!!.size - 1)
        kitBackStack.value!!.add(pageName)
        supportFragmentManager.popBackStack()
        viewArray?.let {
            val transition = getTransition(it)
            transition?.let {
                fragment.enterTransition = transition.first
                fragment.returnTransition = transition.second
            }
        }
        val tz: FragmentTransaction = supportFragmentManager.beginTransaction()
        tz.add(android.R.id.content, fragment)
        tz.addToBackStack(null)
        return commitHandler(tz, pageName)
    }

    fun removeAllAddFragmentNullBackStack(
        fragment: Fragment,
        @StringRes name: Int,
        viewArray: List<Int>?
    ): Boolean? {

        val tag = fragment.javaClass.canonicalName ?: fragment.javaClass.name
        val isFragmentFind = supportFragmentManager.findFragmentByTag(tag) != null
        if (fragment.isStateSaved or fragment.isAdded and isFragmentFind) {
            return null
        }
        var i = 0
        val pageName = baseContext.resources.getString(name)
        kitBackStack.value = mutableListOf()
        kitBackStack.value!!.add(pageName)
        while (supportFragmentManager.fragments.size > i) {
            supportFragmentManager.popBackStack()
            i++
        }
        viewArray?.let {
            val transition = getTransition(it)
            transition?.let {
                fragment.enterTransition = transition.first
                fragment.returnTransition = transition.second
            }
        }
        val tz: FragmentTransaction = supportFragmentManager.beginTransaction()
        tz.add(android.R.id.content, fragment, fragment.javaClass.name)
        tz.addToBackStack(null)
        return commitHandler(tz, pageName)
    }

    private fun executePendingTransaction() {
        try {
            supportFragmentManager.executePendingTransactions()
        } catch (e: IllegalStateException) {
            KitLog.e(e)
        }
    }

    private fun getTransition(viewArray: List<Int>): Pair<Slide, Fade>? {
        if (Build.VERSION.SDK_INT > 19) {
            val slide = Slide()
            slide.interpolator = HalfInterpolator()
            slide.slideEdge = Gravity.BOTTOM
            slide.duration = 500
            viewArray.forEach { item ->
                slide.addTarget(item)
            }
            val fade = Fade()
            fade.interpolator = FastOutSlowInInterpolator()
            return Pair(slide, fade)
        }
        return null
    }


    private fun commitHandler(tz: FragmentTransaction, pageName: String): Boolean {
        try {
            tz.commit()
            return true
        } catch (e: Exception) {
            KitLog.e(e)
        }
        try {
            tz.commitAllowingStateLoss()
            return true
        } catch (e: Exception) {
            KitLog.e(e)
        }
        return false
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    protected fun getResourceDrawable(drawableResourceId: Int): Drawable? {
        return AppCompatResources.getDrawable(this, drawableResourceId)
    }

    protected fun getResourceColor(colorResourceId: Int): Int {
        return ContextCompat.getColor(this, colorResourceId)
    }

    protected fun getDimen(dimenResourceId: Int): Float {
        return resources.getDimension(dimenResourceId)
    }

    protected fun showSnackbar(
        view: View?,
        @StringRes message: Int,
        rtl: Boolean
    ) {
        showSnackbar(view, getString(message), rtl)
    }

    protected fun showSnackbar(
        view: View?,
        message: String?,
        rtl: Boolean
    ) {
        Snackbar.make(view!!, message!!, Snackbar.LENGTH_LONG).show()
    }


    protected fun showSnackbar(
        view: View?,
        message: String?
    ) {
        Snackbar.make(view!!, message!!, Snackbar.LENGTH_LONG).show()
    }

    protected fun showSnackbar(
        view: View?,
        @StringRes message: Int,
        rtl: Boolean,
        length: Int
    ) {
        Snackbar.make(view!!, message, length).show()
    }

    private fun updateLanguagePreference(
        selectedLanguage: String?,
        context: AppCompatActivity
    ) {
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLayoutDirection(Locale(selectedLanguage))
        conf.locale = Locale(selectedLanguage)
        res.updateConfiguration(conf, dm)
    }


    fun closeKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    fun isEmailValid(target: CharSequence?): Boolean {
        return if (target == null) false
        else Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


}