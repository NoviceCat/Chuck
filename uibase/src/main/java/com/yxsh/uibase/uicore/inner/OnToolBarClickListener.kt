package com.yxsh.uibase.uicore.inner

import android.view.View
import com.yxsh.uibase.uicore.view.ToolBarView

/**
 * @author novice
 */
interface OnToolBarClickListener {

    fun onClickToolBarView(view: View, event: ToolBarView.ViewType)
}