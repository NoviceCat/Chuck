package com.yxsh.uicore.module.customtoolbar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uicore.R
import com.yxsh.uicore.util.DefaultViewModel
import kotlinx.android.synthetic.main.view_toolbar_custom.*

class CustomToolbarActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CustomToolbarActivity::class.java))
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_custom_toolbar
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun getCustomToolBarLayoutResId(): Int {
        return R.layout.view_toolbar_custom
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        tv_back.setOnClickListener {
            finish()
        }
    }

}
