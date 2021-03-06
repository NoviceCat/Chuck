package com.zxtx.uibase.application

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import com.blankj.utilcode.util.Utils
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import com.zxtx.uibase.utils.SmartRefreshLayoutUtils
import com.zxtx.uibase.web.sonic.DefaultSonicRuntimeImpl

/**
 * @author novice
 */
abstract class Core : Application() {
    companion object {
        private lateinit var instance: Core
        private lateinit var handler: Handler

        fun getContext(): Application = instance
        fun getHanlder(): Handler = handler
    }

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

    override fun onCreate() {
        super.onCreate()
        instance = this
        handler = Handler(Looper.getMainLooper())
        initBlankj()
        SmartRefreshLayoutUtils.init()
        initSonic()
    }

    private fun initBlankj() {
        Utils.init(this)
    }

    private fun initSonic() {
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(DefaultSonicRuntimeImpl(getContext()), SonicConfig.Builder().build())
        }
    }

}