package com.zxtx.sjd.module.container

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.uicore.statuslayout.StatusLayoutType
import com.zxtx.uibase.uicore.ui.BaseActivity
import com.zxtx.sjd.R
import com.zxtx.sjd.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_container.*

class ContainerActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ContainerActivity::class.java))
        }
    }

    override fun enbaleFixImmersionAndEditBug(): Boolean {
        return true
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_container
    }

    override fun getCoverStatusLayoutResId(): Int {
        return R.id.tv_content
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun statusLayoutRetry(view: View, status: StatusLayoutType) {
        if (status == StatusLayoutType.STATUS_LOAD_ERROR){
            hideStatusLayout()
            showToast("隐藏了错误布局")
        }
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setToolBarTitle("指定StatusLayout覆盖区域")
        tv_content.setOnClickListener {
            showLoadErrorLayout()
        }
    }

}
