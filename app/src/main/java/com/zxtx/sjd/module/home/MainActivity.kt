package com.zxtx.sjd.module.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.BarUtils
import com.zxtx.uibase.decoration.SpaceDecoration
import com.zxtx.uibase.manager.GridLayoutManagerWrap
import com.zxtx.uibase.uicore.ui.BaseActivity
import com.zxtx.sjd.R
import com.zxtx.sjd.module.container.ContainerActivity
import com.zxtx.sjd.module.customstatuslayout.CustomStatusLayout1Activity
import com.zxtx.sjd.module.customstatuslayout.CustomStatusLayout2Activity
import com.zxtx.sjd.module.customtoolbar.CustomToolbarActivity
import com.zxtx.sjd.module.dialog.CommonAlertDialogActivity
import com.zxtx.sjd.module.interior.InteriorActivity
import com.zxtx.sjd.module.list.ListActivity
import com.zxtx.sjd.module.statuslayout.StatusLayoutActivity
import com.zxtx.sjd.module.viewpager2.DragActivity
import com.zxtx.sjd.module.viewpager2.ViewPager2Activity
import com.zxtx.sjd.module.web.CommonWebActivity
import com.zxtx.sjd.module.widget.WidgetActivity
import com.zxtx.sjd.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<DefaultViewModel>() {

    override fun enabledVisibleToolBar(): Boolean {
        return false
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setImmersionBar()
        initRecyclerView()
    }

    private fun setImmersionBar() {
        val params = view_immersion.layoutParams
        params.height = BarUtils.getStatusBarHeight()
        view_immersion.layoutParams = params
    }

    private fun initRecyclerView() {
        val adapter = MainAdapter()
        val list = mutableListOf(
            "基础控件", "CommonAlertDialog", "StatusLayout", "自定义StatusLayout", "完全自定义StatusLayout",
            "指定StatusLayout覆盖区域", "Activity和Fragment交互", "自定义toolbar", "SimpleList", "CommonWeb",
            "ViewPager2", "drag"
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManagerWrap(this, 2)
        recyclerView.addItemDecoration(SpaceDecoration(10))
        adapter.setNewInstance(list)
        adapter.setOnItemClickListener { _, _, position ->
            when (adapter.getItem(position)) {
                "基础控件" -> {
                    WidgetActivity.start(this)
                }
                "CommonAlertDialog" -> {
                    CommonAlertDialogActivity.start(this)
                }
                "StatusLayout" -> {
                    StatusLayoutActivity.start(this)
                }
                "自定义StatusLayout" -> {
                    CustomStatusLayout1Activity.start(this)
                }
                "完全自定义StatusLayout" -> {
                    CustomStatusLayout2Activity.start(this)
                }
                "指定StatusLayout覆盖区域" -> {
                    ContainerActivity.start(this)
                }
                "Activity和Fragment交互" -> {
                    InteriorActivity.start(this)
                }
                "自定义toolbar" -> {
                    CustomToolbarActivity.start(this)
                }
                "SimpleList" -> {
                    ListActivity.start(this)
                }
                "CommonWeb" -> {
                    CommonWebActivity.start(this, "https://www.baidu.com/")
                }
                "ViewPager2" -> {
                    ViewPager2Activity.start(this)
                }
                "drag" -> {
                    DragActivity.start(this)
                }
            }
        }
    }

}
