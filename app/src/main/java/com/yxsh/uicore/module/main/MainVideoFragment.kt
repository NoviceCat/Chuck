package com.yxsh.uicore.module.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.uicore.ui.BaseFragment
import com.yxsh.uicore.R
import com.yxsh.uicore.util.DefaultViewModel

/**
 * @Author : novice
 * @Date : 2021/12/31
 * @Desc : video
 */
class MainVideoFragment : BaseFragment<DefaultViewModel>() {

    companion object {
        fun newInstance(): MainVideoFragment {
            return MainVideoFragment().apply {
                val bundle = Bundle()
                arguments = bundle
            }
        }
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.frg_main_video
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {

    }

}