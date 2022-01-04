package com.zxtx.sjd.module.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.uicore.ui.BaseFragment
import com.zxtx.sjd.R
import com.zxtx.sjd.util.DefaultViewModel

/**
 * @Author : novice
 * @Date : 2021/12/31
 * @Desc : message
 */
class MainMessageFragment : BaseFragment<DefaultViewModel>() {

    companion object {
        fun newInstance(): MainMessageFragment {
            return MainMessageFragment().apply {
                val bundle = Bundle()
                arguments = bundle
            }
        }
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.frg_main_msg
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {

    }

}