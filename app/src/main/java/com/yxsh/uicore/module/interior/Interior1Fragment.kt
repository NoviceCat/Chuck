package com.yxsh.uicore.module.interior

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uibase.uicore.ui.BaseFragment
import com.yxsh.uibase.uicore.view.ToolBarView
import com.yxsh.uibase.utils.Extra
import com.yxsh.uicore.R
import com.yxsh.uicore.util.DefaultViewModel
import kotlinx.android.synthetic.main.fragment_interior1.*

class Interior1Fragment : BaseFragment<DefaultViewModel>() {

    private var province = ""
    private var city = ""

    override fun enabledVisibleToolBar(): Boolean {
        return true
    }

    override fun enabledDefaultBack(): Boolean {
        return true
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_interior1
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setToolBarTitle("Interior1")

        if (getDataIn() is Bundle) {
            val args = getDataIn() as Bundle
            province = args.getString(Extra.arg1) ?: ""
            city = args.getString(Extra.arg2) ?: ""
        }

        tv_content1.text = "收到Activity传递过来的参数：${province} ${city}"
        btn_start.setOnClickListener {
            val args = Bundle()
            args.putString(Extra.arg1, "丰泽区")
            args.putString(Extra.arg2, "万达广场")
            val baseActivity = activity as BaseActivity<*>
            baseActivity.pushFragmentToBackStack(Interior2Fragment::class.java, args)
        }
    }

    override fun onBackWithData(data: Any?) {
        /**
         * 如果当前Fragment和下一个Fragment的Toolbar沉浸式状态栏颜色不一致，
         * 则需要在返回当前Fragment的时候调用initImmersionBar重置沉浸式状态栏，
         * 不然会出现沉浸式状态颜色相同问题
         * 如果当前Fragment和下一个Fragment的Toolbar沉浸式状态栏颜色一致，
         * 则不需要调用initImmersionBar
         */
//        initImmersionBar()
        if (data is Bundle) {
            val project1 = data.getString(Extra.arg1) ?: ""
            val project2 = data.getString(Extra.arg2) ?: ""
            tv_content2.text = "收到Interior2Fragment传递过来的参数：${project1} ${project2}"
        }
    }

}