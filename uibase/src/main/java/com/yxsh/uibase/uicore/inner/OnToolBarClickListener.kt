package com.yxsh.uibase.uicore.inner

import android.view.View
import com.yxsh.uibase.uicore.view.ToolBarView

/**
 * @author novice
 * @date 2020/1/16
 */
interface OnToolBarClickListener {

    fun onClickToolBarView(view: View, event: ToolBarView.ViewType)
}