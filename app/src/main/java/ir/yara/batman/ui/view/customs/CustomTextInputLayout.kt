package ir.yara.batman.ui.view.customs

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout


class CustomTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : TextInputLayout(context, attrs, defStyleRes) {
    private var calligraphyTypeface: Typeface? = null


    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)
        if (enabled) {
            try {
                val layout = this
                val errorView: TextView =
                    ((this.getChildAt(1) as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(0) as TextView
                errorView.typeface = calligraphyTypeface
                (layout.getChildAt(1) as ViewGroup).layoutParams.width = LayoutParams.MATCH_PARENT
                (layout.getChildAt(1) as ViewGroup).getChildAt(0).layoutParams.width =
                    FrameLayout.LayoutParams.MATCH_PARENT

                errorView.gravity = Gravity.RIGHT
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun setError(errorText: CharSequence?) {
        super.setError(errorText)

        isErrorEnabled = (errorText != null)

        try {
            val layout = this
            val errorView: TextView =
                ((this.getChildAt(1) as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(0) as TextView
            errorView.typeface = calligraphyTypeface
            (layout.getChildAt(1) as ViewGroup).layoutParams.width = LayoutParams.MATCH_PARENT
            (layout.getChildAt(1) as ViewGroup).getChildAt(0).layoutParams.width =
                FrameLayout.LayoutParams.MATCH_PARENT

            errorView.visibility = if (errorText == null) View.GONE else View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        if (child is EditText) {
            calligraphyTypeface?.let {
                typeface = calligraphyTypeface
            }
        }
    }

    override fun setHelperText(helperText: CharSequence?) {
        super.setHelperText(helperText)
        try {
            val layout = this
            val helperView: TextView =
                ((this.getChildAt(1) as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(0) as TextView
            helperView.typeface = calligraphyTypeface
            (layout.getChildAt(1) as ViewGroup).layoutParams.width = LayoutParams.MATCH_PARENT
            (layout.getChildAt(1) as ViewGroup).getChildAt(0).layoutParams.width =
                FrameLayout.LayoutParams.MATCH_PARENT

            helperView.visibility = if (helperText == null) View.GONE else View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
