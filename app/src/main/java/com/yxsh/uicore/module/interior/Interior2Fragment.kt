package com.yxsh.uicore.module.interior

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uibase.uicore.ui.BaseFragment
import com.yxsh.uibase.utils.Extra
import com.yxsh.uicore.R
import com.yxsh.uicore.util.DefaultViewModel
import kotlinx.android.synthetic.main.fragment_interior2.*

class Interior2Fragment : BaseFragment<DefaultViewModel>() {

    private var location = ""
    private var desc = ""

    override fun enabledVisibleToolBar(): Boolean {
        return true
    }

    override fun enabledDefaultBack(): Boolean {
        return true
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_interior2
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setToolBarTitle("Interior2")

        if (getDataIn() is Bundle) {
            val args = getDataIn() as Bundle
            location = args.getString(Extra.arg1) ?: ""
            desc = args.getString(Extra.arg2) ?: ""
        }

        tv_content.text = "收到Interior1Fragment传递过来的参数：${location} ${desc}"
        btn_start.setOnClickListener {
            val args = Bundle()
            args.putString(Extra.arg1, "娱播")
            args.putString(Extra.arg2, "视你")
            val baseActivity = activity as BaseActivity<*>
            baseActivity.popTopFragment(args)
        }

    }


}