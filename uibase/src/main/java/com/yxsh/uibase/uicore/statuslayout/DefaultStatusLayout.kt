package com.yxsh.uibase.uicore.statuslayout

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.yxsh.uibase.R
import kotlinx.android.synthetic.main.view_status_layout.view.*

/**
 * 状态页面显示。位置显示居中，靠上
 * @author novice
 * @date 2020/6/24
 */
class DefaultStatusLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_status_layout, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.statusLayout)
        val image = typedArray.getResourceId(R.styleable.statusLayout_imageSrc, View.NO_ID)
        val text1 = typedArray.getString(R.styleable.statusLayout_tipText)
        val text2 = typedArray.getResourceId(R.styleable.statusLayout_tipText, View.NO_ID)
        typedArray.recycle()

        if (image > 0) {
            setImage(image)
        }
        if (!TextUtils.isEmpty(text1)) {
            setTipText(text1 ?: "")
        } else if (text2 != View.NO_ID) {
            setTipText(text2)
        }
    }

    fun setImage(@DrawableRes resId: Int) {
        iv_status_image.setImageResource(resId)
    }

    fun setTipText(@StringRes strId: Int) {
        tv_status_text.setText(strId)
    }

    fun setTipText(sequence: String) {
        tv_status_text.text = sequence
    }

    fun setTipText(sequence: CharSequence) {
        tv_status_text.text = sequence
    }

    fun setTipTextColor(@ColorRes id: Int){
        tv_status_text.setTextColor(ContextCompat.getColor(context, id))
    }

    fun setRefreshBackgroundColor(@ColorRes id: Int) {
        rtv_refresh.delegate.backgroundColor = ContextCompat.getColor(context, id)
    }

    fun getTextView(): AppCompatTextView {
        return tv_status_text
    }

    fun getImageView(): AppCompatImageView {
        return iv_status_image
    }

    fun setDefaultBackgroundColor(@ColorRes id: Int) {
        ll_base_root.setBackgroundColor(ContextCompat.getColor(context, id))
    }

    fun setDefaultBackgroundResource(@DrawableRes resource: Int) {
        ll_base_root.setBackgroundResource(resource)
    }

    fun setVisibleRefresh(isShow: Boolean) {
        rtv_refresh.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    fun setErrorClickListener(listener: OnClickListener?) {
        rtv_refresh.setOnClickListener(listener)
    }

    fun setEmptyClickListener(listener: OnClickListener?) {
        setOnClickListener(listener)
    }

}