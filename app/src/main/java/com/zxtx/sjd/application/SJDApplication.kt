package com.zxtx.sjd.application

import com.blankj.utilcode.util.LogUtils
import com.mob.adsdk.AdConfig
import com.mob.adsdk.AdSdk
import com.mob.videosdk.VideoConfig
import com.mob.videosdk.VideoSdk
import com.tencent.bugly.Bugly
import com.tencent.mmkv.MMKV
import com.zxtx.uibase.application.Core
import com.zxtx.uibase.glide.GlideUtils
import com.zxtx.uibase.uicore.utils.UICoreConfig
import com.zxtx.sjd.BuildConfig
import com.zxtx.sjd.R
import com.zxtx.sjd.constants.ThirdConstants
import com.zxtx.sjd.inner.CatchUICoreThrowableImpl
import com.zxtx.sjd.inner.Constant
import com.zxtx.sjd.network.manager.LoadUtils


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