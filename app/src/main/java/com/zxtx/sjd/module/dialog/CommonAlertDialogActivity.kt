package com.zxtx.sjd.module.dialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.dialog.CommonAlertDialog
import com.zxtx.uibase.dialog.inner.DialogClickListener
import com.zxtx.uibase.uicore.ui.BaseActivity
import com.zxtx.uibase.uicore.view.ToolBarView
import com.zxtx.sjd.R
import com.zxtx.sjd.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_common_alert_dialog.*

class CommonAlertDialogActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CommonAlertDialogActivity::class.java))
        }
    }

    override fun initCommonToolBarBg(): ToolBarView.ToolBarBg {
        return ToolBarView.ToolBarBg.GRA_RED
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_common_alert_dialog
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setToolBarTitle("CommonAlertDialog")
        btn_1.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.Confirm
                title = "标题"
                content = "我是内容"
                leftBtnText = "左边按钮"
                rightBtnText = "右边按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onLeftBtnClick(view: View) {
                        showToast("点击了左边按钮")
                    }

                    override fun onRightBtnClick(view: View) {
                        showToast("点击了右边按钮")
                    }
                }
            }.show()
        }
        btn_2.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.Confirm
                content = "我是内容"
                leftBtnText = "左边按钮"
                rightBtnText = "右边按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onLeftBtnClick(view: View) {
                        showToast("点击了左边按钮")
                    }

                    override fun onRightBtnClick(view: View) {
                        showToast("点击了右边按钮")
                    }
                }
            }.show()
        }
        btn_3.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.SINGLE
                title = "标题"
                content = "我是内容"
                confirmBtnText = "确定按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onConfirmBtnClick(view: View) {
                        showToast("点击了确定按钮")
                    }
                }
            }.show()
        }
        btn_4.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.SINGLE
                content = "我是内容"
                confirmBtnText = "确定按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onConfirmBtnClick(view: View) {
                        showToast("点击了确定按钮")
                    }
                }
            }.show()
        }
        btn_5.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.Edit
                title = "标题"
                hint = "编辑框提示语"
                leftBtnText = "左边按钮"
                rightBtnText = "右边按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onLeftBtnClick(view: View) {
                        showToast("点击了左边按钮")
                    }

                    override fun onRightEditBtnClick(view: View, content: String?) {
                        showToast("点击了右边按钮")
                    }

                }
            }.show()
        }
        btn_6.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.Edit
                hint = "编辑框提示语"
                leftBtnText = "左边按钮"
                rightBtnText = "右边按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onLeftBtnClick(view: View) {
                        showToast("点击了左边按钮")
                    }

                    override fun onRightEditBtnClick(view: View, content: String?) {
                        showToast("点击了右边按钮")
                    }
                }
            }.show()
        }
    }

}
