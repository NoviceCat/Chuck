package com.zxtx.sjd.module.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.SizeUtils
import com.zxtx.uibase.decoration.DividerDecoration
import com.zxtx.uibase.glide.GlideUtils
import com.zxtx.uibase.manager.LinearLayoutManagerWrap
import com.zxtx.uibase.uicore.ui.BaseActivity
import com.zxtx.sjd.R
import com.zxtx.sjd.module.loadingview.LoadingViewActivity
import com.zxtx.sjd.module.main.WorldIslandMainActivity
import com.zxtx.sjd.module.roundimageview.ShapealeImageViewActivity
import com.zxtx.sjd.module.swipe.SwipeMenuActivity
import com.zxtx.sjd.module.tablayout.TablayoutActivity
import com.zxtx.sjd.util.zxing.ScanActivity
import com.zxtx.sjd.util.DefaultViewModel
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
