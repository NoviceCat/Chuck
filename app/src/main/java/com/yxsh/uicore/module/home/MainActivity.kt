package com.yxsh.uicore.module.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.BarUtils
import com.yxsh.uibase.decoration.SpaceDecoration
import com.yxsh.uibase.manager.GridLayoutManagerWrap
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uicore.R
import com.yxsh.uicore.module.container.ContainerActivity
import com.yxsh.uicore.module.customstatuslayout.CustomStatusLayout1Activity
import com.yxsh.uicore.module.customstatuslayout.CustomStatusLayout2Activity
import com.yxsh.uicore.module.customtoolbar.CustomToolbarActivity
import com.yxsh.uicore.module.dialog.CommonAlertDialogActivity
import com.yxsh.uicore.module.interior.InteriorActivity
import com.yxsh.uicore.module.list.ListActivity
import com.yxsh.uicore.module.main.WorldIslandMainActivity
import com.yxsh.uicore.module.statuslayout.StatusLayoutActivity
import com.yxsh.uicore.module.viewpager2.DragActivity
import com.yxsh.uicore.module.viewpager2.ViewPager2Activity
import com.yxsh.uicore.module.web.CommonWebActivity
import com.yxsh.uicore.module.widget.WidgetActivity
import com.yxsh.uicore.util.DefaultViewModel
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
            "ViewPager2", "drag","世界岛首页"
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
                "世界岛首页" -> {
                    WorldIslandMainActivity.start(this)
                }
            }
        }
    }

}
