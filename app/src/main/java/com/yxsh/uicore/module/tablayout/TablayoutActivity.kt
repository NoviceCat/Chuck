package com.yxsh.uicore.module.tablayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.adapter.PagerFragmentAdapter
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uicore.R
import com.yxsh.uicore.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_tablayout.*

class TablayoutActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TablayoutActivity::class.java))
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_tablayout
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setToolBarTitle("TabLayout+ViewPager")
        setToolBarBottomLineVisible(true)
        val size = 6
        val titles = arrayListOf<String>()
        val fragments = arrayListOf<Fragment>()
        for (index in 1..size){
            titles.add("标题${index}")
            if (index%2 ==0){
                fragments.add(IncomeListFragment())
            }else{
                fragments.add(ExpendListFragment())
            }
        }
        val adapter = PagerFragmentAdapter<Fragment>(supportFragmentManager, titles)
        adapter.setFragments(fragments)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = size//这边必须设置limit，否则切换Fragment再回到对应Fragment数据会丢失
        tabLayout.setViewPager(viewPager)
    }

}
