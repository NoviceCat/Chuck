package com.zxtx.sjd.module.web

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.uicore.view.ToolBarView
import com.zxtx.uibase.utils.Extra
import com.zxtx.uibase.web.SonicWebActivity
import com.zxtx.sjd.R
import com.zxtx.sjd.module.web.js.AndroidInterface
import com.zxtx.sjd.module.web.js.JSBridgeLifeCycleListener
import com.zxtx.sjd.module.web.js.JSBridgeUtils
import com.zxtx.sjd.util.DefaultViewModel

/**
 * @author novice
 */
class CommonWebActivity : SonicWebActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context, url: String) {
            start(context, url, "")
        }

        fun start(context: Context, url: String, title: String) {
            start(context, url, title, ToolBarView.ToolBarBg.WHITE)
        }

        fun start(context: Context, url: String, toolBarBg: ToolBarView.ToolBarBg) {
            start(context, url, "", toolBarBg)
        }

        /**
         * @param url 网页地址
         * @param title 标题栏
         * @param toolBarBg webView标题栏颜色
         */
        fun start(context: Context, url: String, title: String, toolBarBg: ToolBarView.ToolBarBg) {
            val intent = Intent(context, CommonWebActivity::class.java)
            intent.putExtra(Extra.URL, url)
            intent.putExtra(Extra.TITLE, title)
            intent.putExtra(Extra.COLOR, toolBarBg)
            context.startActivity(intent)
        }
    }

    override fun onClickToolBarView(view: View, event: ToolBarView.ViewType) {
        if (event == ToolBarView.ViewType.LEFT_IMAGE) {
            if (event == ToolBarView.ViewType.LEFT_IMAGE) {
                if (agentWeb != null) {
                    if (!agentWeb!!.back()) {
                        finish()
                    } else {
                        agentWeb!!.back()
                    }
                } else {
                    finish()
                }
            }
        }
    }

    override fun hostUrl(): String {
        return intent.getStringExtra(Extra.URL) ?: ""
    }

    override fun indicatorColor(): Int {
        return Color.parseColor("#FC3F65")
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        super.initView(rootView, savedInstanceState)
        val androidInterface = AndroidInterface(this, agentWeb!!)
        androidInterface.setJSBridgeLifeCycleListener(object : JSBridgeLifeCycleListener {

            override fun setTitle(title: String?) {
                if (isViewDestroyed()) return
                setToolBarTitle(title ?: "")
            }

            override fun setBarRightText(text: String?) {
                if (isViewDestroyed()) return
                setToolBarRightText(text ?: "")
            }

            override fun setBarRightIcon(icon: String?) {
                if (isViewDestroyed()) return
                JSBridgeUtils.asyncGetBitmap(this@CommonWebActivity, icon, callback = { bitmap ->
                    if (bitmap == null) {
                        setToolBarRightImage(R.drawable.icon_more)
                        return@asyncGetBitmap
                    }
                    setRightImageScaleType(ImageView.ScaleType.CENTER)
                    setRightImage(bitmap)
                })
            }

            override fun showProgressDialog() {
                if (isViewDestroyed()) return
                showProgressDialog()
            }

            override fun hideProgressDialog() {
                if (isViewDestroyed()) return
                hideProgressDialog()
            }

            override fun showToast(msg: String?) {
                if (isViewDestroyed()) return
                showToast(msg)
            }

            override fun finish() {
                if (isViewDestroyed()) return
                finishAc()
            }

        })
        addJavaObject("appMethod", androidInterface)
    }

}