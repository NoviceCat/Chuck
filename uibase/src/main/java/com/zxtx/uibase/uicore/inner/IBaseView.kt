package com.zxtx.uibase.uicore.inner

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.*
import com.zxtx.uibase.uicore.statuslayout.OnStatusCustomClickListener
import com.zxtx.uibase.uicore.statuslayout.StatusLayoutManager
import com.zxtx.uibase.uicore.statuslayout.StatusLayoutType
import com.zxtx.uibase.uicore.view.ToolBarView

/**
 * 用户页面, 操作页面，对应Activity,fragment统一接口层
 * @author novice
 */
interface IBaseView : ICoreView {

    /**
     * 就否支持默认有返回按键的 toolBar
     *
     * @return
     */
    fun enabledDefaultBack(): Boolean

    /**
     * 当前页面Fragment支持沉浸式初始化。默认返回false，可设置支持沉浸式初始化
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    fun enabledImmersion(): Boolean

    /**
     * 是否开启解决沉浸式状态栏与edittext冲突问题(常见场景：聊天页属于沉浸式并且布局底部有edittext，如果没开启的话，底部的edittext弹出软键盘后没法跟着一起顶上去)
     */
    fun enbaleFixImmersionAndEditBug(): Boolean


    /**
     * 获取自定义toolBarView 资源id 默认为-1，showToolBar()方法必须返回true才有效
     *
     * @return
     */
    @LayoutRes
    fun getToolBarLayoutResId(): Int

    @LayoutRes
    fun getCustomToolBarLayoutResId(): Int

    fun enabledVisibleToolBar(): Boolean

    /**
     * enabledCommonToolBar 为true时有效。
     * 设置中心title
     *
     * @param title
     */
    fun setToolBarTitle(title: String): ToolBarView?

    /**
     * enabledCommonToolBar 为true时有效。
     * 设置中心title
     *
     * @param resId
     */
    fun setToolBarTitle(@StringRes resId: Int): ToolBarView?

    /**
     * enabledCommonToolBar 为true时有效。
     * 设置中心title颜色
     *
     * @param resId
     */
    fun setToolBarTitleColor(@ColorRes resId: Int): ToolBarView?

    /**
     * enabledCommonToolBar 为true时有效。
     * 设置中心title颜色
     *
     * @param resId
     */
    fun setToolBarTitleColorInt(@ColorInt resId: Int): ToolBarView?

    fun initCommonToolBarBg(): ToolBarView.ToolBarBg

    fun setRightImageScaleType(scaleType: ImageView.ScaleType): ToolBarView?

    fun setRightImage(bm: Bitmap): ToolBarView?

    /**
     * enabledCommonToolBar 为true时有效。
     * 设置右侧 图标
     *
     * @param drawable
     */
    fun setToolBarRightImage(@DrawableRes drawable: Int): ToolBarView?

    /**
     * enabledCommonToolBar 为true时有效。
     * 设置右侧 文字
     *
     * @param text
     */
    fun setToolBarRightText(text: String): ToolBarView?

    /**
     * enabledCommonToolBar 为true时有效。
     * 设置右侧 文字颜色
     *
     * @param text
     */
    fun setToolBarRightTextColor(@ColorRes resId: Int): ToolBarView?

    /**
     * enabledCommonToolBar 为true时有效。
     * 设置右侧 文字颜色
     *
     * @param text
     */
    fun setToolBarRightTextColorInt(@ColorInt resId: Int): ToolBarView?

    /**
     * enabledCommonToolBar 为true时有效。
     * 设置右侧 文字
     *
     * @param resId
     */
    fun setToolBarRightText(@StringRes resId: Int): ToolBarView?


    fun setToolBarViewVisible(isVisible: Boolean, vararg events: ToolBarView.ViewType): ToolBarView?

    fun setToolBarBottomLineVisible(isVisible: Boolean): ToolBarView?

    /**
     * 初始化UI
     *
     * @param rootView
     * @param savedInstanceState
     */
    fun initView(rootView: View, savedInstanceState: Bundle?)

    /**
     * 初始化数据，在InitView后调用
     */
    fun initData()

    fun initLazyData()

    /**
     * 根视图View
     *
     * @return
     */
    fun getRootView(): View

    /**
     * activity和fragment
     * 统一处理返回
     */
    fun onBackPressed()

    /**
     * 若用户使用自定义toolbar，而且还想使用statusLayout，则需要重写此方法，返回statusLayout覆盖的内容区域resId
     *
     * @return
     */
    @IdRes
    fun getCoverStatusLayoutResId(): Int

    /**
     * 当需要自定义status layout时，需要重写此方法，注意不需要调用builder.build()方法。
     *
     * @param builder
     * @return
     */
    fun buildCustomStatusLayoutView(builder: StatusLayoutManager.Builder): StatusLayoutManager.Builder

    /**
     * 调用status layout，显示自定义Layout
     */
    fun showCustomLayout(@LayoutRes customLayoutID: Int, onStatusCustomClickListener: OnStatusCustomClickListener, @IdRes vararg clickViewID: Int)

    /**
     * status layout 加载失败，重试
     *
     * @param view
     */
    fun statusLayoutRetry(view: View)

    fun statusLayoutRetry(view: View, status: StatusLayoutType)

}