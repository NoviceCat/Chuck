package com.yxsh.uicore.module.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.SizeUtils
import com.yxsh.uibase.decoration.DividerDecoration
import com.yxsh.uibase.glide.GlideUtils
import com.yxsh.uibase.manager.LinearLayoutManagerWrap
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uicore.R
import com.yxsh.uicore.module.loadingview.LoadingViewActivity
import com.yxsh.uicore.module.main.WorldIslandMainActivity
import com.yxsh.uicore.module.roundimageview.ShapealeImageViewActivity
import com.yxsh.uicore.module.swipe.SwipeMenuActivity
import com.yxsh.uicore.module.tablayout.TablayoutActivity
import com.yxsh.uicore.module.zxing.ScanActivity
import com.yxsh.uicore.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_widget.*

class WidgetActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, WidgetActivity::class.java))
        }
    }

    override fun enabledVisibleToolBar(): Boolean {
        return false
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_widget
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        GlideUtils.load(this, R.drawable.banner, img_banner)
        val adapter = WidgetAdapter()
        val list = mutableListOf(
            "世界岛首页",
            "QRCodeScan",
            "EasySwipeMenuLayout",
            "TabLayout+ViewPager",
            "LoadingView",
            "ShapeableImageView"
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManagerWrap(this)
        recyclerView.addItemDecoration(
            DividerDecoration(
                Color.parseColor("#F2F2F2"),
                SizeUtils.dp2px(1f),
                SizeUtils.dp2px(15F),
                0
            )
        )
        adapter.setNewInstance(list)
        adapter.setOnItemClickListener { _, _, position ->
            when (adapter.getItem(position)) {
                "DotView" -> {
                }
                "WheelView" -> {
                }
                "EasySwipeMenuLayout" -> {
                    SwipeMenuActivity.start(this)
                }
                "TabLayout+ViewPager" -> {
                    TablayoutActivity.start(this)
                }
                "LoadingView" -> {
                    LoadingViewActivity.start(this)
                }
                "MsgView" -> {
                }
                "ToggleButton" -> {
                }
                "ShapeableImageView" -> {
                    ShapealeImageViewActivity.start(this)
                }
                "QRCodeScan" -> {
                    ScanActivity.start(this)
                }
                "世界岛首页" -> {
                    WorldIslandMainActivity.start(this)
                }
            }
        }
    }

}
