package com.zxtx.sjd.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zxtx.sjd.R
import com.zxtx.sjd.util.DefaultViewModel
import com.zxtx.sjd.util.zxing.ScanActivity
import com.zxtx.uibase.dialog.CommonAlertDialog
import com.zxtx.uibase.dialog.inner.DialogClickListener
import com.zxtx.uibase.uicore.ui.BaseActivity
import kotlinx.android.synthetic.main.act_touch.*

/**
 * @Author : novice
 * @Date : 2022/4/11
 * @Desc :
 */
class TouchActivity : BaseActivity<DefaultViewModel>() {

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, TouchActivity::class.java))
        }
    }


    override fun getLayoutResId() = R.layout.act_touch

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    var mDialog: CommonAlertDialog? = null

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        super.initView(rootView, savedInstanceState)

        tv_click.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
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
                        mDialog = this
                    }.show()
                }

                MotionEvent.ACTION_UP -> {
                    mDialog?.dialog?.dismiss()
                }
            }
            false
        }
    }
}