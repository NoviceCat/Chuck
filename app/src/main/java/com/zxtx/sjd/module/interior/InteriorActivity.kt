package com.zxtx.sjd.module.interior

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.uicore.ui.BaseActivity
import com.zxtx.uibase.utils.Extra
import com.zxtx.sjd.R
import com.zxtx.sjd.util.DefaultViewModel

class InteriorActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, InteriorActivity::class.java))
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
        val args = Bundle()
        args.putString(Extra.arg1, "厦门")
        args.putString(Extra.arg2, "思明")
        pushFragmentToBackStack(Interior1Fragment::class.java, args)
    }

}
