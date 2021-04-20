package com.kr.hkslibrary.iwanttomakematerialedittext.customedittextoutlineborder

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.text.InputFilter
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.use
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import com.kr.hkslibrary.iwanttomakematerialedittext.R
import com.kr.hkslibrary.iwanttomakematerialedittext.databinding.LayoutCustomEditText

/**
 * inverseBindingMethod에 대해서 inverseBindingAdapter에 대해서 좀 공부해야할 듯?
 * 코드를 한번씩 보면서 라이브러리 개발자가 어떤 생각으로 작성했는지 이해하는 것이 중요해보인다.
 */
@InverseBindingMethods(value = [InverseBindingMethod(type = CustomEditTextOutLineBorder::class, attribute = "textValue", event = "android:textAttrChanged", method = "getTextValue"),
    InverseBindingMethod(type = CustomEditTextOutLineBorder::class, attribute = "errorTextValue"),
    InverseBindingMethod(type = CustomEditTextOutLineBorder::class, attribute = "isErrorEnable")])
class CustomEditTextOutLineBorder @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0,
    defStyleRes : Int = 0
) : LinearLayout(context,attrs,defStyle,defStyleRes) {

    var binding : LayoutCustomEditText =
        LayoutCustomEditText.inflate(LayoutInflater.from(context),this,true)

    /**
     * 여기 있는 값들을 전부 delegate로 바꿔본다면 어떨까?
     */
    private var titleColor = ContextCompat.getColor(context, R.color.color_brownish_grey_two)
    private var titleErrorColor = ContextCompat.getColor(context , R.color.color_error)
    private var borderColor = ContextCompat.getColor(context,R.color.color_warm_grey)
    private var borderErrorColor = ContextCompat.getColor(context,R.color.color_error)
    private var borderWidth = 1

    private var title : String = ""
    private var editTextHint = ""
    private var isErrorEnable = true
    private var inputType = EditorInfo.TYPE_TEXT_VARIATION_NORMAL
    private var maxLine = 1
    private var minLine = 1
    private var maxLength = 99
    private var titleBackgroundColor = ContextCompat.getColor(context,R.color.colorPrimary)
    private var editTextBackgroundColor = ContextCompat.getColor(context,R.color.colorPrimary)
    private var errorTextBackgroundColor = ContextCompat.getColor(context,R.color.colorPrimary)

    init {
        orientation = VERTICAL
        if(attrs != null){
            getStyleableAttrs(attrs)
        }
    }

    private fun getStyleableAttrs(attr : AttributeSet) {
        context.theme.obtainStyledAttributes(attr,R.styleable.custom_component_attributes,0,0).use {
            title = resources.getText(it.getResourceId(R.styleable.custom_component_attributes_custom_component_title,R.string.app_name)) as String
            editTextHint = resources.getText(it.getResourceId(R.styleable.custom_component_attributes_custom_component_editText_hint,R.string.app_name)) as String
            isErrorEnable = it.getBoolean(R.styleable.custom_component_attributes_isErrorEnable,false)
            inputType = it.getInt(R.styleable.custom_component_attributes_android_inputType,EditorInfo.TYPE_TEXT_VARIATION_NORMAL)
            maxLine = it.getInt(R.styleable.custom_component_attributes_custom_component_maxline,1)
            minLine = it.getInt(R.styleable.custom_component_attributes_custom_component_minline,1)
            maxLength = it.getInt(R.styleable.custom_component_attributes_custom_component_maxLength , 99)
            titleBackgroundColor = it.getColor(R.styleable.custom_component_attributes_custom_component_title_bg_color,ContextCompat.getColor(context,R.color.colorPrimary))
            editTextBackgroundColor = it.getColor(R.styleable.custom_component_attributes_custom_component_editText_bg_color,ContextCompat.getColor(context,R.color.colorPrimary))
            errorTextBackgroundColor = it.getColor(R.styleable.custom_component_attributes_custom_component_error_text_bg_color,ContextCompat.getColor(context,R.color.colorPrimary))
            titleColor = it.getColor(R.styleable.custom_component_attributes_custom_component_title_color,ContextCompat.getColor(context,R.color.color_brownish_grey_two))
            titleErrorColor = it.getColor(R.styleable.custom_component_attributes_custom_component_title_error_color,ContextCompat.getColor(context,R.color.color_error))
            borderColor = it.getColor(R.styleable.custom_component_attributes_custom_component_border_color,ContextCompat.getColor(context,R.color.color_warm_grey))
            borderErrorColor = it.getColor(R.styleable.custom_component_attributes_custom_component_border_error_color,ContextCompat.getColor(context,R.color.color_error))
            borderWidth = it.getColor(R.styleable.custom_component_attributes_custom_component_border_width,1)

            setTitle(title)
            setEditTextHint(editTextHint)
            setTextStyle(ResourcesCompat.getFont(context, R.font.graphik_regular))
            setIsErrorEnable(isErrorEnable)
            setStyle(inputType, maxLine, minLine, maxLength)
            setTitleBackGroundColor(titleBackgroundColor)
            setEditTextBackGroundColor(editTextBackgroundColor)
            setErrorTextBackGroundColor(errorTextBackgroundColor)
        }
    }

    fun setTextValue(value : String?){
        value?.let {
            binding.editText.setText(value)
            binding.editText.setSelection(value.length)
        }
    }

    fun setIsErrorEnable(isShown: Boolean) {
        if (isShown) {
            setTitleColor(titleErrorColor)
            setBackgroundBorderErrorColor(borderErrorColor)
            binding.lableError.visibility = View.VISIBLE
        } else {
            setTitleColor(titleColor)
            setBackgroundBorderErrorColor(borderColor)
            binding.lableError.visibility = View.GONE
        }
    }

    private fun setTitleColor(@ColorInt colorID: Int) {
        binding.lableTitle.setTextColor(colorID)
    }

    private fun setTitle(title: String) {
        binding.lableTitle.text = title
    }

    private fun setTitleBackGroundColor(@ColorInt colorID: Int) {
        binding.lableTitle.setBackgroundColor(colorID)
    }

    private fun setErrorTextBackGroundColor(@ColorInt colorID: Int) {
        binding.lableError.setBackgroundColor(colorID)
    }

    private fun setEditTextBackGroundColor(@ColorInt colorID: Int) {
        val drawable = binding.editText.background as StateListDrawable
        val dcs = drawable.constantState as DrawableContainer.DrawableContainerState?
        val drawableItems = dcs!!.children
        val gradientDrawableChecked = drawableItems[0] as GradientDrawable
        gradientDrawableChecked.setColor(colorID)
    }

    private fun setEditTextHint(hint: String) {
        binding.editText.hint = hint
    }

    private fun setStyle(inputType: Int, maxLine: Int, minLine: Int, maxLength: Int) {
        binding.editText.inputType = inputType
        binding.editText.apply {
            maxLines = maxLine
            minLines = minLine
            gravity = Gravity.TOP or Gravity.START
            filters = arrayOf(InputFilter.LengthFilter(maxLength))
        }
    }

    private fun setBackgroundBorderErrorColor(@ColorInt colorID: Int) {
        val drawable = binding.editText.background as StateListDrawable
        val dcs = drawable.constantState as DrawableContainer.DrawableContainerState?
        val drawableItems = dcs!!.children
        val gradientDrawableChecked = drawableItems[0] as GradientDrawable
        gradientDrawableChecked.setStroke(borderWidth, colorID)
    }

    private fun setTextStyle(textStyle: Typeface?) {
        binding.lableTitle.typeface = textStyle
        binding.editText.typeface = textStyle
        binding.lableError.typeface = textStyle
    }
}