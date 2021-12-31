package com.yxsh.uibase.uicore.statuslayout

import android.view.View

/**
 * 状态布局中 点击事件
 * @author novice
 * @date 2020/6/24
 */
interface OnStatusRetryClickListener {

    /**
     * 布局子 View 被点击
     *
     * @param view
     * @param status 点击布局
     */
    fun onClickRetry(view: View, status: StatusLayoutType)

}