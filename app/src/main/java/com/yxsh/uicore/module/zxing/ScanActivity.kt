package com.yxsh.uicore.module.zxing

import android.content.Context
import android.content.Intent
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uicore.util.DefaultViewModel
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.yxsh.uicore.R
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_scan_layout.*

/**
 * @Author : novice
 * @Date : 2021/12/31
 * @Desc : scan QRCode
 */
class ScanActivity : BaseActivity<DefaultViewModel>(), QRCodeView.Delegate {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ScanActivity::class.java))
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_scan_layout
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        super.initView(rootView, savedInstanceState)
        zxingview.setDelegate(this)
    }

    override fun onResume() {
        super.onResume()
        zxingview.startSpot()
    }

    override fun onStart() {
        super.onStart()
        zxingview.startCamera()
        zxingview.showScanRect()
    }

    override fun onStop() {
        zxingview.stopCamera()
        super.onStop()
    }

    public override fun onDestroy() {
        zxingview.onDestroy()
        super.onDestroy()
    }

    override fun onScanQRCodeSuccess(result: String) {
        zxingview.startSpot()
        ToastUtils.showShort("scan result == $result")
        LogUtils.e(result)
    }

    override fun onScanQRCodeOpenCameraError() {
        ToastUtils.showShort("打开相机出错")
    }
}