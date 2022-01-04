package com.zxtx.sjd.module.statuslayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.uicore.statuslayout.StatusLayoutType
import com.zxtx.uibase.uicore.ui.BaseActivity
import com.zxtx.uibase.uicore.view.ToolBarView
import com.zxtx.sjd.R
import com.zxtx.sjd.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_statuslayout.*

class StatusLayoutActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, StatusLayoutActivity::class.java))
        }
    }

    override fun initCommonToolBarBg(): ToolBarView.ToolBarBg {
        return ToolBarView.ToolBarBg.BLACK
    }

    override fun statusLayoutRetry(view: View, status: StatusLayoutType) {
        when (status) {
            StatusLayoutType.STATUS_EMPTY -> {
                hideStatusLayout()
                showToast("隐藏了空布局")
            }
            StatusLayoutType.STATUS_LOAD_ERROR -> {
                hideStatusLayout()
                showToast("隐藏了错误布局")
            }
            StatusLayoutType.STATUS_NET_DISCONNECT_ERROR -> {
                hideStatusLayout()
                showToast("隐藏了网络断开布局")
            }
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_statuslayout
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setToolBarTitle("StatusLayout")
        btn_loading.setOnClickListener {
            showLoadingLayout()
        }
        btn_empty.setOnClickListener {
            showEmptyLayout()
        }
        btn_error.setOnClickListener {
            showLoadErrorLayout()
        }
        btn_net_disconnect.setOnClickListener {
            showNetDisconnectLayout()
        }
        btn_dialog.setOnClickListener {
            showProgressDialog()
        }
    }

}
