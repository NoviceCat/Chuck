package com.yxsh.uicore.module.viewpager2

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.uicore.ui.BaseFragment
import com.yxsh.uibase.utils.Extra
import com.yxsh.uicore.R
import kotlinx.android.synthetic.main.fragment_left.*

/**
 * @author novice
 * @date 2020/8/28
 */
class VLeftFragment : BaseFragment<ViewPagerViewModel>() {

    companion object {
        fun newInstance(liveID: Int): VLeftFragment {
            return VLeftFragment().apply {
                val bundle = Bundle()
                bundle.putInt(Extra.arg1, liveID)
                arguments = bundle
            }
        }
    }

    override fun initViewModel(): ViewPagerViewModel {
        return ViewModelProvider(this).get(ViewPagerViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_left
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        val liveID = arguments?.getInt(Extra.arg1) ?: 0
        tv_title.text = liveID.toString()
    }

    override fun registorUIChangeLiveDataCallBack() {
        viewModel.updateItemEvent.observe(viewLifecycleOwner, Observer {
            tv_title.text = it.toString()
        })
    }

    override fun initData() {
        viewModel.updateItem()
    }


}