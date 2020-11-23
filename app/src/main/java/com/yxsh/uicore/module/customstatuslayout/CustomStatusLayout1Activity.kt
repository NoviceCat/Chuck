package com.yxsh.uicore.module.customstatuslayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.uicore.statuslayout.StatusLayoutManager
import com.yxsh.uibase.uicore.statuslayout.StatusLayoutType
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uibase.uicore.view.ToolBarView
import com.yxsh.uicore.R
import com.yxsh.uicore.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_custom_status_layout.*

class CustomStatusLayout1Activity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CustomStatusLayout1Activity::class.java))
        }
    }

    override fun initCommonToolBarBg(): ToolBarView.ToolBarBg {
        return ToolBarView.ToolBarBg.GRA_YELLOW
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_custom_status_layout1
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
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

    override fun buildCustomStatusLayoutView(builder: StatusLayoutManager.Builder): StatusLayoutManager.Builder {
        builder.setDefaultEmptyImg(R.drawable.ic_status_layout_load_empty1)
        builder.setDefaultEmptyText("我是自定义空布局内容")
        builder.setDefaultLoadErrorImg(R.drawable.ic_status_layout_load_failure1)
        builder.setDefaultLoadErrorText("我是自定义错误布局内容")
        builder.setDefaultNetDisconnectImg(R.drawable.ic_status_layout_net_disconnect1)
        builder.setDefaultNetDisconnectText("我是自定义网络断开布局内容")
        builder.setDefaultThemeColor(R.color.color_FFBD01)
        builder.setDefaultStatusTextColor(R.color.white)
        builder.setDefaultLayoutsBackgroundResource(R.drawable.custom_bg)
        return builder
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setToolBarTitle("自定义StatusLayout")
        btn_empty.setOnClickListener {
            showEmptyLayout()
        }
        btn_error.setOnClickListener {
            showLoadErrorLayout()
        }
        btn_net_disconnect.setOnClickListener {
            showNetDisconnectLayout()
        }
    }
}
