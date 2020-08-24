package ir.yara.batman.ui.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import java.util.*


open class BaseActivity : AppCompatActivity() {


    val LOG_TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


    fun disableScreenShot() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    fun enableScreenShot() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
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