package com.zxtx.sjd.module.viewpager2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.uicore.ui.BaseActivity
import com.zxtx.sjd.R
import com.zxtx.sjd.util.DefaultViewModel

class DragActivity : BaseActivity<DefaultViewModel>() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, DragActivity::class.java))
        }
    }

    override fun enabledVisibleToolBar(): Boolean {
        return false
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_interior
    }

    override fun getFragmentContainerId(): Int {
        return R.id.container
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        pushFragmentToBackStack(VRightFragment::class.java, null)
    }

}