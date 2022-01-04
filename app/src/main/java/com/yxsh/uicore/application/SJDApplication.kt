package com.yxsh.uicore.application

import com.blankj.utilcode.util.LogUtils
import com.tencent.bugly.Bugly
import com.tencent.mmkv.MMKV
import com.yxsh.uibase.application.Core
import com.yxsh.uibase.glide.GlideUtils
import com.yxsh.uibase.uicore.utils.UICoreConfig
import com.yxsh.uicore.BuildConfig
import com.yxsh.uicore.R
import com.yxsh.uicore.inner.CatchUICoreThrowableImpl
import com.yxsh.uicore.inner.Constant
import com.yxsh.uicore.network.LoadUtils

/**
 * @author novice
 */
class SJDApplication : Core() {

    override fun onCreate() {
        super.onCreate()
        initUICoreConfig()
        LoadUtils.init()
        initMMKV()
    }

    private fun initUICoreConfig() {
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

    // 初始化 MMKV数据存储
    private fun initMMKV() {
        val rootDir: String = MMKV.initialize(this)
        LogUtils.d("Novic_Log", "mmkv root: $rootDir")
    }

}