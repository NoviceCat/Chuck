package com.yxsh.uicore.application

import com.blankj.utilcode.util.LogUtils
import com.tencent.bugly.Bugly
import com.yxsh.uibase.BuildConfig
import com.yxsh.uibase.application.Core
import com.yxsh.uibase.glide.GlideUtils
import com.yxsh.uibase.uicore.utils.UICoreConfig
import com.yxsh.uicore.inner.CatchUICoreThrowableImpl
import com.yxsh.uicore.inner.Constant
import com.yxsh.uicore.R

/**
 * @author novice
 * @date 2020/6/2
 */
class MyApplication : Core() {

    override fun onCreate() {
        super.onCreate()
        UICoreConfig.let {
            it.mode = BuildConfig.DEBUG
            it.setCatchThrowableListener(CatchUICoreThrowableImpl())
            it.defaultThemeColor = R.color.common_red_color
            it.defaultEmptyIcon = R.drawable.ic_status_layout_load_empty
            it.loadErrorIcon = R.drawable.ic_status_layout_load_failure
            it.netDisconnectIcon = R.drawable.ic_status_layout_net_disconnect
            it.loadingLottie = "commonLoadingLottie.zip"
            it.progressLottie = "commonProgressLottie.json"
        }
        Bugly.init(this, Constant.BUGLY.APP_ID, true)
        GlideUtils.initConfig(R.drawable.ic_placeholer, R.drawable.ic_placeholer)
        LogUtils.getConfig().isLogSwitch = BuildConfig.DEBUG
    }

}