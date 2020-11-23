package com.yxsh.uicore.module.loadingview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uicore.R
import com.yxsh.uicore.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_loading_view.*

class LoadingViewActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoadingViewActivity::class.java))
        }
    }

    override fun enabledVisibleToolBar(): Boolean {
        return false
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_loading_view
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setToolBarTitle("LoadingView")
        loading_view.start()
    }

}
