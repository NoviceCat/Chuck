package com.yxsh.uibase.uicore.inner

import androidx.annotation.StringRes

/**
 * ViewModel与View的契约接口
 * @author novic
 * @date 2020/1/18
 */
interface ICoreView {

    /**
     * 显示进度dialog
     */
    fun showProgressDialog()

    /**
     * 隐藏进度dialog
     */
    fun hideProgressDialog()


    /**
     * 调用status layout，显示空
     */
    fun showEmptyLayout()

    /**
     * 调用status layout，显示加载中...
     */
    fun showLoadingLayout()

    /**
     * 调用status layout，显示加载失败
     */
    fun showLoadErrorLayout()

    /**
     * 调用status layout，显示网络断开
     */
    fun showNetDisconnectLayout()

    /**
     * 隐藏status layout
     */
    fun hideStatusLayout()

    /**
     * 吐司
     */
    fun showToast(msg: String?)

    /**
     * 长吐司
     */
    fun showLongToast(msg: String?)

    /**
     * 吐司
     */
    fun showToast(@StringRes resId: Int)

    /**
     * 长吐司
     */
    fun showLongToast(@StringRes resId: Int)

    /**
     * 显示在屏幕中间的吐司
     */
    fun showCenterToast(msg: String?)

    /**
     * 显示在屏幕中间的长吐司
     */
    fun showCenterLongToast(msg: String?)

    /**
     * 显示在屏幕中间的吐司
     */
    fun showCenterToast(@StringRes resId: Int)

    /**
     * 显示在屏幕中间的长吐司
     */
    fun showCenterLongToast(@StringRes resId: Int)

    /**
     * 关闭Activity
     */
    fun finishAc()

}