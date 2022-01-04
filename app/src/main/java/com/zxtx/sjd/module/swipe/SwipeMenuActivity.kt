package com.zxtx.sjd.module.swipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.uicore.ui.BaseActivity
import com.zxtx.uibase.widget.swipe.State
import com.zxtx.sjd.R
import com.zxtx.sjd.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_swipe_menu.*

class SwipeMenuActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SwipeMenuActivity::class.java))
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_swipe_menu
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        btn_open.setOnClickListener {
            swipe_menu.handlerSwipeMenu(State.RIGHTOPEN)
        }
        btn_close.setOnClickListener {
            swipe_menu.handlerSwipeMenu(State.CLOSE)
        }
        img_delete.setOnClickListener {
            showToast("delete")
        }

    }

}
