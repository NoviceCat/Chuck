package com.zxtx.sjd.module.customstatuslayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.zxtx.uibase.uicore.statuslayout.OnStatusCustomClickListener
import com.zxtx.uibase.uicore.statuslayout.StatusLayoutManager
import com.zxtx.uibase.uicore.ui.BaseActivity
import com.zxtx.uibase.uicore.view.ToolBarView
import com.zxtx.sjd.R
import com.zxtx.sjd.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_custom_status_layout.btn_empty
import kotlinx.android.synthetic.main.activity_custom_status_layout.btn_error
import kotlinx.android.synthetic.main.activity_custom_status_layout.btn_net_disconnect
import kotlinx.android.synthetic.main.activity_custom_status_layout2.*

class CustomStatusLayout2Activity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CustomStatusLayout2Activity::class.java))
        }
    }

    override fun initCommonToolBarBg(): ToolBarView.ToolBarBg {
        return ToolBarView.ToolBarBg.RED
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_custom_status_layout2
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun buildCustomStatusLayoutView(builder: StatusLayoutManager.Builder): StatusLayoutManager.Builder {
        val emptyView = LayoutInflater.from(this).inflate(R.layout.view_custom_empty_status_layout, null)
        val errorView = LayoutInflater.from(this).inflate(R.layout.view_custom_error_status_layout, null)
        val netDisconnectView = LayoutInflater.from(this).inflate(R.layout.view_custom_netdisconnet_status_layout, null)
        val imgContent = emptyView.findViewById<AppCompatImageView>(R.id.img_content)
        val btnErrorRefresh = errorView.findViewById<MaterialButton>(R.id.btn_refresh)
        val btnNetDisconnectRefresh = netDisconnectView.findViewById<MaterialButton>(R.id.btn_refresh)
        builder.setEmptyLayout(emptyView)
        builder.setLoadErrorLayout(errorView)
        builder.setNetDisconnectLayout(netDisconnectView)
        imgContent.setOnClickListener {
            hideStatusLayout()
            showToast("??????????????????")
        }
        btnErrorRefresh.setOnClickListener {
            hideStatusLayout()
            showToast("?????????????????????")
        }
        btnNetDisconnectRefresh.setOnClickListener {
            hideStatusLayout()
            showToast("???????????????????????????")
        }
        return builder
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setToolBarTitle("???????????????StatusLayout")
        btn_empty.setOnClickListener {
            showEmptyLayout()
        }
        btn_error.setOnClickListener {
            showLoadErrorLayout()
        }
        btn_net_disconnect.setOnClickListener {
            showNetDisconnectLayout()
        }
        btn_custom.setOnClickListener {
            showCustomLayout(R.layout.view_custom_status_layout, object : OnStatusCustomClickListener {
                override fun onCustomClick(view: View) {
                    when (view.id) {
                        R.id.btn1 -> {
                            hideStatusLayout()
                            showToast("????????????????????????")
                        }
                        R.id.btn2 -> {
                            showToast("??????????????????")
                        }
                    }
                }
            }, R.id.btn1, R.id.btn2)
        }
    }

}
