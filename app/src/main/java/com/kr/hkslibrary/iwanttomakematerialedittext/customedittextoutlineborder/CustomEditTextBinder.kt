package com.kr.hkslibrary.iwanttomakematerialedittext.customedittextoutlineborder

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import androidx.databinding.adapters.TextViewBindingAdapter
import com.kr.hkslibrary.iwanttomakematerialedittext.R

object CustomEditTextBinder {

    /**
     * This binding adapter is used to set custom edittext value.
     *
     * @param customEditText
     * @param value
     */
    @JvmStatic
    @BindingAdapter("textValue")
    fun setTextValue(customEditText: CustomEditTextOutLineBorder, value: String?) {
        value?.let {
            customEditText.setTextValue(value)
        }
    }

    @JvmStatic
    @BindingAdapter("errorTextValue")
    fun setErrorTextValue(customEditText: CustomEditTextOutLineBorder, value: String?) {
        value?.let {
            customEditText.binding.lableError.text = value
        }
    }

    @JvmStatic
    @BindingAdapter("isErrorEnable")
    fun setIsErrorEnable(customEditText: CustomEditTextOutLineBorder, value: Boolean) {
        customEditText.setIsErrorEnable(value)
    }

    @JvmStatic
    @BindingAdapter(value = ["android:afterTextChanged", "android:textAttrChanged"], requireAll = false)
    fun setTextWatcher(filterPositionView: CustomEditTextOutLineBorder, test: TextViewBindingAdapter.AfterTextChanged?, textAttrChanged: InverseBindingListener?) {
        val newValue = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                test?.let {
                    test.afterTextChanged(s)
                }

                textAttrChanged?.let {
                    textAttrChanged.onChange()
                }
            }
        }
        val oldValue = ListenerUtil.trackListener(filterPositionView.binding.editText, newValue, R.id.textWatcher)
        if (oldValue != null) {
            filterPositionView.binding.editText.removeTextChangedListener(oldValue)
        }
        filterPositionView.binding.editText.addTextChangedListener(newValue)
    }
}