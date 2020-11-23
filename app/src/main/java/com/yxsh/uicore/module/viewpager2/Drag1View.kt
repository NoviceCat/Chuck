package com.yxsh.uicore.module.viewpager2

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.yxsh.uicore.R

/**
 * 移动控件
 * @author novic
 * @date 2020/4/28
 */
class Drag1View @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_drag_item1, this, true)
    }

}