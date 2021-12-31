package com.yxsh.uibase.uicore.ui

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yxsh.uibase.R
import com.yxsh.uibase.uicore.inner.IBaseView
import com.yxsh.uibase.uicore.inner.OnToolBarClickListener
import com.yxsh.uibase.uicore.statuslayout.*
import com.yxsh.uibase.uicore.utils.FragmentStarter
import com.yxsh.uibase.uicore.utils.OSUtils
import com.yxsh.uibase.uicore.utils.UICoreConfig
import com.yxsh.uibase.uicore.view.CommonViewDelegate
import com.yxsh.uibase.uicore.view.ToolBarView
import com.yxsh.uibase.uicore.viewmodel.BaseViewModel

/**
 * activity基类
 * @author novice
 * @date 2020/1/16
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), IBaseView, OnToolBarClickListener {

    private val TAG = this.javaClass.simpleName

    lateinit var viewModel: VM

    private var componentView: CommonViewDelegate? = null

    /**
     * activity is destroyed
     */
    private var isViewDestroy = false

    /**
     * 通用toolBar
     */
    private var toolbarView: ToolBarView? = null

    /**
     * 顶部 bar，若使用通用toolbar则为ToolBarView。否则为自定义的top bar view
     */
    private var topBarView: View? = null

    /**
     * 通用的自定义toolbar，填充内容
     */
    protected var childContainerLayout: FrameLayout? = null

    /**
     * 在使用自定义toolbar时候的根布局 =toolBarView+childView
     */
    private var rootView: View? = null

    /**
     * 默认显示，当不显示时，设置为false
     * 用户控件dialog的显示，注意，不能onResume判断，因为有些页面在初始化时，就要弹出dialog加载进度条。
     */
    private var isActivityVisible = true

    /**
     * 重写getResources()方法，让APP的字体不受系统设置字体大小影响
     */
    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            attachViewModelAndLifecycle()
            initContentView()
            initStatusLayoutCoverView()
            initImmersionBar()
            initCommonToolBar()
            initView(getRootView(), savedInstanceState)
            initUIChangeLiveDataCallBack()
            initData()
            postInitLazyData()
        } catch (e: Throwable) {
            catchThrowable(e)
        }
    }

    private fun attachViewModelAndLifecycle() {
        viewModel = initViewModel()
        lifecycle.addObserver(viewModel)
    }

    private fun postInitLazyData() {
        if (getRootView() != null) {
            val runnable = Runnable {
                if (!isFinishing && !isViewDestroyed()) {
                    initLazyData()
                }
            }
            getRootView().post(runnable)
        }
    }

    override fun initLazyData() {
    }

    private fun getViewDelegate(): CommonViewDelegate {
        if (componentView == null) {
            componentView = CommonViewDelegate(this)
        }
        return componentView!!
    }

    /**
     * 注册ViewModel与View的契约UI回调事件
     */
    private fun initUIChangeLiveDataCallBack() {
        //ProgressDialog
        viewModel.showProgressDialogEvent.observe(this, Observer {
            showProgressDialog()
        })
        viewModel.hideProgressDialogEvent.observe(this, Observer {
            hideProgressDialog()
        })
        //StatusLayout
        viewModel.showEmptyLayoutEvent.observe(this, Observer {
            showEmptyLayout()
        })
        viewModel.showLoadingLayoutEvent.observe(this, Observer {
            showLoadingLayout()
        })
        viewModel.showNetDisconnectLayoutEvent.observe(this, Observer {
            showNetDisconnectLayout()
        })
        viewModel.showLoadErrorLayoutEvent.observe(this, Observer {
            showLoadErrorLayout()
        })
        viewModel.hideStatusLayoutEvent.observe(this, Observer {
            hideStatusLayout()
        })
        //Toast
        viewModel.showToastStrEvent.observe(this, Observer { t ->
            showToast(t)
        })
        viewModel.showLongToastStrEvent.observe(this, Observer { t ->
            showLongToast(t)
        })
        viewModel.showToastResEvent.observe(this, Observer { t ->
            showToast(t)
        })
        viewModel.showLongToastResEvent.observe(this, Observer { t ->
            showLongToast(t)
        })
        viewModel.showCenterToastStrEvent.observe(this, Observer { t ->
            showCenterToast(t)
        })
        viewModel.showCenterLongToastStrEvent.observe(this, Observer { t ->
            showCenterLongToast(t)
        })
        viewModel.showCenterToastResEvent.observe(this, Observer { t ->
            showCenterToast(t)
        })
        viewModel.showCenterLongToastResEvent.observe(this, Observer { t ->
            showCenterLongToast(t)
        })
        viewModel.finishAcEvent.observe(this, Observer {
            finishAc()
        })
        registorUIChangeLiveDataCallBack()
    }

    open fun registorUIChangeLiveDataCallBack() {

    }

    override fun initData() {
    }

    override fun getRootView(): View {
        return rootView!!
    }

    override fun onResume() {
        super.onResume()
        this.isActivityVisible = true
    }

    override fun onPause() {
        super.onPause()
        this.isActivityVisible = false
    }

    override fun onDestroy() {
        try {
            hideProgressDialog()
            this.isViewDestroy = true
            this.isActivityVisible = false
            super.onDestroy()
            rootView = null
        } catch (e: Throwable) {
            catchThrowable(e)
        }
    }

    open fun isViewDestroyed(): Boolean {
        return isViewDestroy
    }

    /**
     * 获取layout resource id
     *
     * @return 布局layout
     */
    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    abstract fun initViewModel(): VM

    protected open fun getFragmentContainerId(): Int {
        return 0
    }

    private fun initContentView() {
        val layoutResID: Int = getLayoutResId()
        if (layoutResID == 0) {
            return
        }
        //根布局
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_base, null, false)
        //toolbar容器
        val toolbarVs = rootView!!.findViewById<ViewStub>(R.id.vs_toolbar)
        //子布局容器
        childContainerLayout = rootView!!.findViewById(R.id.fl_container)
        if (enabledVisibleToolBar()) {
            val toolbarId = if (isShowCustomToolbar()) getCustomToolBarLayoutResId() else getToolBarLayoutResId()
            //toolbar资源id
            toolbarVs.layoutResource = toolbarId
            //填充toolbar
            topBarView = toolbarVs.inflate()
        }
        //子布局
        LayoutInflater.from(this).inflate(layoutResID, childContainerLayout, true)
        super.setContentView(rootView)
    }

    /**
     * 设置statusLayout
     */
    private fun initStatusLayoutCoverView() {
        var container: View? = null
        val resId = getCoverStatusLayoutResId()
        if (resId != 0) {
            getRootView()?.let {
                container = getRootView().findViewById(resId)
            }
        } else {
            container = childContainerLayout
        }
        Log.d("status layout", "status layout cover container view $container")
        container?.let { buildStatusView(it) } ?: Log.e("status layout", "status layout cover container view is null")
    }

    /**
     * 是过滤器显示通用toolBar
     *
     * @return
     */
    private fun isShowCustomToolbar(): Boolean {
        return getCustomToolBarLayoutResId() != 0
    }

    override fun enabledVisibleToolBar(): Boolean {
        return true
    }

    override fun getCoverStatusLayoutResId(): Int {
        return 0
    }

    override fun enabledDefaultBack(): Boolean {
        return true
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
    }

    private var closeToast: Toast? = null

    protected open fun processBackPressed(): Boolean {
        return false
    }

    override fun onBackPressed() {
        if (processBackPressed()) {
            return
        }
        back()
    }

    fun back() {
        var enableBackPressed = true
        //说明，只有当通过pushFragmentToBackStack方式调用，才会回调processBackPressed方法。
        if (mCurrentFragment != null) {
            enableBackPressed = !mCurrentFragment!!.processBackPressed()
        }
        log(" getBackStackEntryCount  " + supportFragmentManager.backStackEntryCount + "   " + enableBackPressed + ";mCurrentFragment:" + mCurrentFragment)

        if (!enableBackPressed) {
            return
        }
        val cnt = supportFragmentManager.backStackEntryCount
        if (cnt <= 1 && isTaskRoot) {
            val closeWarningHint = "再按一次退出程序"
            if (!mCloseWarned && !TextUtils.isEmpty(closeWarningHint)) {
                closeToast = Toast.makeText(applicationContext, closeWarningHint, Toast.LENGTH_SHORT)
                closeToast!!.show()
                mCloseWarned = true
                Handler().postDelayed({ mCloseWarned = false }, 1500)
            } else {
                if (closeToast != null) {
                    closeToast!!.cancel()
                }
                doReturnBack()
            }
        } else {
            mCloseWarned = false
            doReturnBack()
        }
    }

    private fun doReturnBack() {
        val count = supportFragmentManager.backStackEntryCount
        if (count <= 1) {
            finish()
        } else {
            supportFragmentManager.popBackStackImmediate()
            if (tryToUpdateCurrentAfterPop() && mCurrentFragment != null) {
                mCurrentFragment!!.onBackWithData(null)
            }
        }
    }

    protected open fun log(args: String?) {
        LogUtils.i(TAG, args)
    }

    override fun showToast(msg: String?) {
        msg?.let {
            ToastUtils.setGravity(-1, -1, -1)
            ToastUtils.showShort(it)
        }
    }

    override fun showLongToast(msg: String?) {
        msg?.let {
            ToastUtils.setGravity(-1, -1, -1)
            ToastUtils.showLong(it)
        }
    }

    override fun showToast(resId: Int) {
        ToastUtils.setGravity(-1, -1, -1)
        ToastUtils.showShort(resId)
    }

    override fun showLongToast(resId: Int) {
        ToastUtils.setGravity(-1, -1, -1)
        ToastUtils.showLong(resId)
    }

    override fun showCenterToast(msg: String?) {
        msg?.let {
            ToastUtils.setGravity(Gravity.CENTER, 0, 0)
            ToastUtils.showShort(msg)
        }
    }

    override fun showCenterLongToast(msg: String?) {
        msg?.let {
            ToastUtils.setGravity(Gravity.CENTER, 0, 0)
            ToastUtils.showLong(msg)
        }
    }

    override fun showCenterToast(resId: Int) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.showShort(resId)
    }

    override fun showCenterLongToast(resId: Int) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.showLong(resId)
    }

    override fun finishAc() {
        finish()
    }

    private fun catchThrowable(e: Throwable) {
        UICoreConfig.throwable(e)
    }

    //------------------toolbar start setting-----------------------------------

    override fun initCommonToolBarBg(): ToolBarView.ToolBarBg {
        return ToolBarView.ToolBarBg.WHITE
    }

    override fun getCustomToolBarLayoutResId(): Int {
        return 0
    }

    protected open fun hasCommonToolBar(): Boolean {
        return toolbarView != null
    }

    /**
     * 初始化toolbar可重写覆盖自定的toolbar,base中实现的是通用的toolbar
     */
    private fun initCommonToolBar() { //toolbar
        toolbarView = findViewById(R.id.toolBarView)
        if (!hasCommonToolBar()) {
            return
        }
        getCommonToolBarView()?.setToolBarClickListener(this)
        //支持默认返回按钮和事件
        setToolBarViewVisible(enabledDefaultBack(), ToolBarView.ViewType.LEFT_IMAGE)
    }

    override fun setToolBarTitle(title: String): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setCenterText(title)
        } else {
            return null
        }
    }

    override fun setToolBarTitle(@StringRes idRes: Int): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setCenterText(idRes)
        } else {
            return null
        }
    }

    override fun setToolBarTitleColor(@ColorRes resId: Int): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setCenterTextColor(resId)
        } else {
            return null
        }
    }

    override fun setToolBarTitleColorInt(@ColorInt resId: Int): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setCenterTextColorInt(resId)
        } else {
            return null
        }
    }

    override fun setRightImageScaleType(scaleType: ImageView.ScaleType): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setRightImageScaleType(scaleType)
        } else {
            return null
        }
    }

    override fun setRightImage(bm: Bitmap): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setRightImage(bm)
        } else {
            return null
        }
    }

    override fun setToolBarRightImage(@DrawableRes drawable: Int): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setRightImage(drawable)
        } else {
            return null
        }
    }

    override fun setToolBarRightText(@StringRes resId: Int): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setRightText(resId)
        } else {
            return null
        }
    }

    override fun setToolBarRightText(text: String): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setRightText(text)
        } else {
            return null
        }
    }

    override fun setToolBarRightTextColor(@ColorRes resId: Int): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setRightTextColor(resId)
        } else {
            return null
        }
    }

    override fun setToolBarRightTextColorInt(@ColorInt resId: Int): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setRightTextColorInt(resId)
        } else {
            return null
        }
    }

    override fun setToolBarBottomLineVisible(isVisible: Boolean): ToolBarView? {
        return if (hasCommonToolBar()) {
            getCommonToolBarView()?.showBottomLine(isVisible)
        } else null
    }

    override fun setToolBarViewVisible(isVisible: Boolean, vararg events: ToolBarView.ViewType): ToolBarView? {
        if (hasCommonToolBar()) {
            return getCommonToolBarView()?.setToolBarViewVisible(isVisible, *events)
        } else {
            return null
        }
    }


    protected open fun getCommonToolBarView(): ToolBarView? {
        return toolbarView
    }

    /**
     * 顶部toolbar，自定义view或通用toolbar
     *
     * @return
     */
    protected open fun getTopToolBar(): View? {
        return topBarView
    }

    override fun onClickToolBarView(view: View, event: ToolBarView.ViewType) {
        when (event) {
            //支持默认返回按钮和事件
            ToolBarView.ViewType.LEFT_IMAGE -> {
                if (enabledDefaultBack()) {
                    onBackPressed()
                }
            }
        }
    }

    //------------------ toolbar end ------------


    //------------------------- 沉浸式 ImmersionBar start --------------------
    override final fun enabledImmersion(): Boolean {
        return true
    }

    override fun enbaleFixImmersionAndEditBug(): Boolean {
        return false
    }


    override fun getToolBarLayoutResId(): Int {
        when (initCommonToolBarBg()) {
            ToolBarView.ToolBarBg.GRA_YELLOW -> return R.layout.include_common_gra_yellow_toolbar
            ToolBarView.ToolBarBg.GRA_RED -> return R.layout.include_common_gra_red_toolbar
            ToolBarView.ToolBarBg.WHITE -> return R.layout.include_common_white_toolbar
            ToolBarView.ToolBarBg.PURPLE -> return R.layout.include_common_purple_toolbar
            ToolBarView.ToolBarBg.GRAY -> return R.layout.include_common_gray_toolbar
            ToolBarView.ToolBarBg.BLACK -> return R.layout.include_common_black_toolbar
            ToolBarView.ToolBarBg.YELLOW -> return R.layout.include_common_yellow_toolbar
            ToolBarView.ToolBarBg.RED -> return R.layout.include_common_red_toolbar
            ToolBarView.ToolBarBg.ORANGE -> return R.layout.include_common_orange_toolbar
            else -> return R.layout.include_common_white_toolbar
        }
    }

    private fun initImmersionBar() {
        if (enabledVisibleToolBar()) {
            when (initCommonToolBarBg()) {
                ToolBarView.ToolBarBg.GRA_YELLOW -> setGraYellowFakeStatus(R.id.ll_base_root, enbaleFixImmersionAndEditBug())
                ToolBarView.ToolBarBg.GRA_RED -> setGraRedFakeStatus(R.id.ll_base_root, enbaleFixImmersionAndEditBug())
                ToolBarView.ToolBarBg.GRAY -> setGrayFakeStatus(R.id.ll_base_root, enbaleFixImmersionAndEditBug())
                ToolBarView.ToolBarBg.PURPLE -> setPurpleFakeStatus(R.id.ll_base_root, enbaleFixImmersionAndEditBug())
                ToolBarView.ToolBarBg.WHITE -> setWhiteFakeStatus(R.id.ll_base_root, enbaleFixImmersionAndEditBug())
                ToolBarView.ToolBarBg.BLACK -> setBlackFakeStatus(R.id.ll_base_root, enbaleFixImmersionAndEditBug())
                ToolBarView.ToolBarBg.YELLOW -> setYellowFakeStatus(R.id.ll_base_root, enbaleFixImmersionAndEditBug())
                ToolBarView.ToolBarBg.RED -> setRedFakeStatus(R.id.ll_base_root, enbaleFixImmersionAndEditBug())
                ToolBarView.ToolBarBg.ORANGE -> setOrangeFakeStatus(R.id.ll_base_root, enbaleFixImmersionAndEditBug())
            }
        } else {
            setTransparentStatus(R.id.ll_base_root, enbaleFixImmersionAndEditBug())
        }
    }

    open fun setGraYellowFakeStatus(contentParentViewId: Int, enbaleFixImmersionAndEditBug: Boolean) {
        setFakeStatus(contentParentViewId, true, 0, R.drawable.common_gra_yellow_color, enbaleFixImmersionAndEditBug)
    }

    open fun setGraRedFakeStatus(contentParentViewId: Int, enbaleFixImmersionAndEditBug: Boolean) {
        setFakeStatus(contentParentViewId, true, 0, R.drawable.common_gra_red_color, enbaleFixImmersionAndEditBug)
    }

    open fun setPurpleFakeStatus(contentParentViewId: Int, enbaleFixImmersionAndEditBug: Boolean) {
        setFakeStatus(contentParentViewId, true, 0, R.color.common_purple_color, enbaleFixImmersionAndEditBug)
    }

    open fun setBlackFakeStatus(contentParentViewId: Int, enbaleFixImmersionAndEditBug: Boolean) {
        setFakeStatus(contentParentViewId, true, 0, R.color.common_black_color, enbaleFixImmersionAndEditBug)
    }

    open fun setGrayFakeStatus(contentParentViewId: Int, enbaleFixImmersionAndEditBug: Boolean) {
        setFakeStatus(contentParentViewId, true, 0, R.color.common_gray_color, enbaleFixImmersionAndEditBug)
    }

    open fun setYellowFakeStatus(contentParentViewId: Int, enbaleFixImmersionAndEditBug: Boolean) {
        setFakeStatus(contentParentViewId, true, 0, R.color.common_yellow_color, enbaleFixImmersionAndEditBug)
    }

    open fun setRedFakeStatus(contentParentViewId: Int, enbaleFixImmersionAndEditBug: Boolean) {
        setFakeStatus(contentParentViewId, true, 0, R.color.common_red_color, enbaleFixImmersionAndEditBug)
    }

    open fun setOrangeFakeStatus(contentParentViewId: Int, enbaleFixImmersionAndEditBug: Boolean) {
        setFakeStatus(contentParentViewId, true, 0, R.color.common_orange_color, enbaleFixImmersionAndEditBug)
    }

    open fun setWhiteFakeStatus(contentParentViewId: Int, enbaleFixImmersionAndEditBug: Boolean) {
        setFakeStatus(contentParentViewId, true, 0, R.color.common_white_color, enbaleFixImmersionAndEditBug)
        OSUtils.fixWhiteStatusbarBug(this)
    }

    open fun setTransparentStatus(contentParentViewId: Int, enbaleFixImmersionAndEditBug: Boolean) {
        val parentView = findViewById<View>(contentParentViewId)
        if (parentView != null) {
            BarUtils.setStatusBarColor(this, Color.argb(100, 0, 0, 0), false).background = null
            BarUtils.subtractMarginTopEqualStatusBarHeight(parentView)
            BarUtils.setStatusBarLightMode(this, true)
        }
        fixImmersionAndEditBug(enbaleFixImmersionAndEditBug)
    }

    private fun setFakeStatus(
        contentParentViewId: Int,
        isLightMode: Boolean,
        alpha: Int,
        statuBgResource: Int,
        enbaleFixImmersionAndEditBug: Boolean
    ) {
        val parentView = findViewById<View>(contentParentViewId)
        if (parentView != null) {
            BarUtils.setStatusBarColor(this, Color.argb(alpha, 0, 0, 0)).setBackgroundResource(statuBgResource)
            BarUtils.addMarginTopEqualStatusBarHeight(parentView)
            BarUtils.setStatusBarLightMode(this, isLightMode)
        }
        fixImmersionAndEditBug(enbaleFixImmersionAndEditBug)
    }

    private fun fixImmersionAndEditBug(enbaleFixImmersionAndEditBug: Boolean) {
        if (enbaleFixImmersionAndEditBug) {
            KeyboardUtils.fixAndroidBug5497(this) //解决沉浸式状态栏与edittext冲突问题
        }
    }

    //------------------------- 沉浸式 ImmersionBar end --------------------


    //--------------------- ProgressDialog start --------------------------
    private fun isActivityVisible(): Boolean {
        return isActivityVisible
    }

    override fun showProgressDialog() {
        if (!isViewDestroyed() && isActivityVisible()) {
            getViewDelegate().showProgressDialog()
        }
    }

    /**
     * ProgressDialog 取消
     *
     * @param onTouchOutside 点击空白区域
     * @param backCancel     点击返回取消
     */
    open fun setProgressDialogCancel(onTouchOutside: Boolean, backCancel: Boolean) {
        if (!isViewDestroyed()) {
            getViewDelegate().setProgressDialogCanceled(onTouchOutside, backCancel)
        }
    }

    override fun hideProgressDialog() {
        if (!isViewDestroyed()) {
            getViewDelegate().hideProgressDialog()
        }
    }

    //--------------------- ProgressDialog end --------------------------


    //--------------------- status layout start --------------------------

    /**
     * 若是不显示通用toolbar，需要传入status覆盖的View.
     *
     * @param contentView
     */
    private fun buildStatusView(contentView: View) {
        log("[buildStatusView]contentView：$contentView")
        getViewDelegate().initStatusLayout(contentView)
        val statusLayoutManagerBuilder = getViewDelegate().statusLayoutManagerBuilder
        val builder = buildCustomStatusLayoutView(statusLayoutManagerBuilder)
        if (builder.onStatusRetryClickListener == null) {
            getViewDelegate().setDefaultStatusListener(object : OnStatusRetryClickListener {
                override fun onClickRetry(view: View, status: StatusLayoutType) {
                    statusLayoutRetry(view, status)
                }
            })
        }
        getViewDelegate().build(builder)
    }

    override fun buildCustomStatusLayoutView(builder: StatusLayoutManager.Builder): StatusLayoutManager.Builder {
        return builder
    }


    override fun statusLayoutRetry(view: View) {

    }

    /**
     * 点击不同状态。
     * StatusLayoutType
     *
     * @param view
     * @param status
     */
    override fun statusLayoutRetry(view: View, status: StatusLayoutType) {

    }

    override fun showEmptyLayout() {
        getViewDelegate().showEmptyLayout()
    }

    override fun showLoadingLayout() {
        getViewDelegate().showLoadingLayout()
    }

    override fun showLoadErrorLayout() {
        getViewDelegate().showLoadErrorLayout()
    }

    override fun showNetDisconnectLayout() {
        getViewDelegate().showNetDisconnectLayout()
    }

    open fun getDefaultEmptyLayout(): DefaultStatusLayout? {
        return getViewDelegate().emptyLayout
    }

    open fun getDefaultLoadErrorLayout(): DefaultStatusLayout? {
        return getViewDelegate().loadErrorLayout
    }

    override fun hideStatusLayout() {
        getViewDelegate().hideStatusLayout()
    }

    override fun showCustomLayout(@LayoutRes customLayoutID: Int, onStatusCustomClickListener: OnStatusCustomClickListener, @IdRes vararg clickViewID: Int) {
        getViewDelegate().showCustomLayout(customLayoutID, onStatusCustomClickListener, *clickViewID)
    }

    //--------------------- status layout end --------------------------


    //--------------------- control fragment start --------------------------

    private var mCurrentFragment: BaseFragment<*>? = null

    open fun getCurrentFragment(): BaseFragment<*>? {
        return mCurrentFragment
    }

    private var mCloseWarned = false

    /**
     * 添加到 stack中
     *
     * @param containerId
     * @param cls
     * @param data
     */
    open fun pushFragmentToBackStack(@IdRes containerId: Int, cls: Class<out BaseFragment<*>>?, data: Any?) {
        try {
            mCurrentFragment = FragmentStarter.pushFragmentToBackStack(this, containerId, cls, data, true)
            mCloseWarned = false
        } catch (e: Throwable) {
            catchThrowable(e)
        }
    }

    open fun pushFragmentToBackStack(cls: Class<out BaseFragment<*>>?, data: Any?) {
        pushFragmentToBackStack(getFragmentContainerId(), cls, data)
    }

    open fun popTopFragment(data: Any?) {
        val fm = supportFragmentManager
        fm.popBackStackImmediate()
        if (tryToUpdateCurrentAfterPop() && mCurrentFragment != null) {
            mCurrentFragment!!.onBackWithData(data)
        }
    }

    private fun tryToUpdateCurrentAfterPop(): Boolean {
        val fm = supportFragmentManager
        val cnt = fm.backStackEntryCount
        if (cnt > 0) {
            val name = fm.getBackStackEntryAt(cnt - 1).name
            val fragment = fm.findFragmentByTag(name)
            if (fragment != null && fragment is BaseFragment<*>) {
                mCurrentFragment = fragment
            }
            return true
        }
        return false
    }

}