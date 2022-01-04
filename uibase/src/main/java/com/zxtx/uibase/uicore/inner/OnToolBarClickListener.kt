package com.zxtx.uibase.uicore.inner

import android.view.View
import com.zxtx.uibase.uicore.view.ToolBarView

/**
 * @author novice
 */
interface OnToolBarClickListener {

    fun onClickToolBarView(view: View, event: ToolBarView.ViewType)
}