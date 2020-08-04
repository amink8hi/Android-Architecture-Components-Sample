package ir.yara.batman.ui.view.customs

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.textfield.TextInputEditText
import ir.yara.batman.R


class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : TextInputEditText(context, attrs, defStyleRes) {

    init {
        attrs?.let {
            initAttrs(context, attrs)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
        }
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {

        val attributeArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEditText
        )

        var drawableLeft: Drawable? = null
        var drawableRight: Drawable? = null
        var drawableBottom: Drawable? = null
        var drawableTop: Drawable? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawableLeft = attributeArray.getDrawable(R.styleable.CustomEditText_drawableLeftCompat)
            drawableRight =
                attributeArray.getDrawable(R.styleable.CustomEditText_drawableRightCompat)
            drawableBottom =
                attributeArray.getDrawable(R.styleable.CustomEditText_drawableBottomCompat)
            drawableTop = attributeArray.getDrawable(R.styleable.CustomEditText_drawableTopCompat)
        } else {
            val drawableLeftId =
                attributeArray.getResourceId(R.styleable.CustomEditText_drawableLeftCompat, -1)
            val drawableRightId =
                attributeArray.getResourceId(R.styleable.CustomEditText_drawableRightCompat, -1)
            val drawableBottomId =
                attributeArray.getResourceId(R.styleable.CustomEditText_drawableBottomCompat, -1)
            val drawableTopId =
                attributeArray.getResourceId(R.styleable.CustomEditText_drawableTopCompat, -1)

            val minusOne = -1
            if (drawableLeftId != minusOne)
                drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId)
            if (drawableRightId != minusOne)
                drawableRight = AppCompatResources.getDrawable(context, drawableRightId)
            if (drawableBottomId != minusOne)
                drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId)
            if (drawableTopId != minusOne)
                drawableTop = AppCompatResources.getDrawable(context, drawableTopId)
        }
        setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            drawableTop,
            drawableRight,
            drawableBottom
        )

        attributeArray.recycle()

        // text change listener to convert persian digit to english
        val editTextInputType = inputType
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // empty block
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (editTextInputType == InputType.TYPE_NUMBER_VARIATION_PASSWORD || editTextInputType == InputType.TYPE_TEXT_VARIATION_PASSWORD ||
                    editTextInputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD || editTextInputType == InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD ||
                    editTextInputType == 129
                ) {
                    return
                }
                val originalText = s.toString()
                val newText = reshapeDigit(originalText)
                if (originalText != newText)
                    setText(newText)
            }

            override fun afterTextChanged(s: Editable) {
                // empty block
            }
        })
    }

    /**
     * convert persian digit to english
     * @param text edit text value
     * @return converted text
     */
    private fun reshapeDigit(text1: String): String {
        var text = text1

        text = text.replace("۰", "0")
        text = text.replace("۱", "1")
        text = text.replace("۲", "2")
        text = text.replace("۳", "3")
        text = text.replace("۴", "4")
        text = text.replace("۵", "5")
        text = text.replace("۶", "6")
        text = text.replace("۷", "7")
        text = text.replace("۸", "8")
        text = text.replace("۹", "9")
        return text
    }
}
