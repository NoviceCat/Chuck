package com.yxsh.uicore.application

import com.blankj.utilcode.util.LogUtils
import com.mob.adsdk.AdConfig
import com.mob.adsdk.AdSdk
import com.mob.videosdk.VideoConfig
import com.mob.videosdk.VideoSdk
import com.tencent.bugly.Bugly
import com.tencent.mmkv.MMKV
import com.yxsh.uibase.application.Core
import com.yxsh.uibase.glide.GlideUtils
import com.yxsh.uibase.uicore.utils.UICoreConfig
import com.yxsh.uicore.BuildConfig
import com.yxsh.uicore.R
import com.yxsh.uicore.constants.ThirdConstants
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
        initAD()
    }

    private fun initAD() {
        // 初始化 AdSdk，视频流中可以展现广告
        AdSdk.getInstance().init(
            applicationContext,
            AdConfig.Builder()
                .appId(ThirdConstants.AD_APP_ID)
                .userId(ThirdConstants.AD_USER_ID) // 未登录可不设置 userId，登录时再设置
                .debug(BuildConfig.DEBUG)
                .build(),
            null
        )

        VideoSdk.getInstance().init(
            applicationContext,
            VideoConfig.Builder()
                .appId(ThirdConstants.AD_APP_ID)
                .userId(ThirdConstants.AD_USER_ID) // 未登录可不设置 userId，登录时再设置
                .debug(BuildConfig.DEBUG)
                .build(),
            null
        )

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