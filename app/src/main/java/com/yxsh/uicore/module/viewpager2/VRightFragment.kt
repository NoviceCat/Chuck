package com.yxsh.uicore.module.viewpager2

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.yxsh.uibase.uicore.ui.BaseFragment
import com.yxsh.uibase.utils.Extra
import com.yxsh.uicore.R
import com.yxsh.uicore.module.list.ListAdapter
import com.yxsh.uicore.module.viewpager2.praiseview.PraiseCallback
import kotlinx.android.synthetic.main.fragment_right.*

/**
 * @author novice
 * @date 2020/8/28
 */
class VRightFragment : BaseFragment<ViewPagerViewModel>() {

    companion object {
        fun newInstance(liveID: Int): VRightFragment {
            return VRightFragment().apply {
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
        return R.layout.fragment_right
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        val liveID = arguments?.getInt(Extra.arg1) ?: 0
        button.text = liveID.toString()
        drag_constraint_layout.addDragView(object : View.OnClickListener {
            override fun onClick(v: View) {
                when (v.id) {
                    R.id.drag_view1 -> {
                        ToastUtils.showLong("我被点击了Drag1")
                    }
                    R.id.drag_view2 -> {
                        ToastUtils.showLong("我被点击了Drag2")
                    }
                }
            }

        }, drag_view1, drag_view2)
        button.setOnClickListener {
//            SharePop(context!!).showPopupWindow()
            button.visibility = View.GONE
        }
        val adapter = ListAdapter()
        adapter.setNewInstance(mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        praise_view.clickCallback = object : PraiseCallback {
            override fun clickAction() {
                if (isViewDestroyed()) return
                periscope.addHeart()
            }

            override fun clickComplete(count: Int) {
                if (isViewDestroyed()) return
            }

        }
    }

    override fun registorUIChangeLiveDataCallBack() {
        viewModel.updateItemEvent.observe(viewLifecycleOwner, Observer {
            button.text = it.toString()
        })
    }

    override fun initData() {
        viewModel.updateItem()
    }


}