package com.yxsh.uicore.module.viewpager2

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.adapter.PagerFragmentAdapter
import com.yxsh.uibase.uicore.ui.BaseFragment
import com.yxsh.uibase.utils.Extra
import com.yxsh.uibase.view.CustomScrollViewPager
import com.yxsh.uicore.R
import com.yxsh.uicore.util.DefaultViewModel
import kotlinx.android.synthetic.main.fragment_viewpager2.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author novice
 * @date 2020/8/28
 */
class ViewPager2Fragment : BaseFragment<DefaultViewModel>() {

    companion object {
        fun newInstance(liveID: Int, position: Int): ViewPager2Fragment {
            return ViewPager2Fragment().apply {
                val bundle = Bundle()
                bundle.putInt(Extra.arg1, liveID)
                bundle.putInt(Extra.POSITION, position)
                arguments = bundle
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventMessage(message: EventMessage) {
        if (message.code == 1) {
            custom_viewpager.setIsCanScroll(false)
        } else if (message.code == 2) {
            custom_viewpager.setIsCanScroll(true)
        }
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_viewpager2
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        val liveID = arguments?.getInt(Extra.arg1) ?: 0
        val position = arguments?.getInt(Extra.POSITION) ?: 0
        tv_content.text = liveID.toString()
        EventBus.getDefault().register(this)
        initViewPager(position)
    }

    private fun initViewPager(position: Int) {
        val fragments = arrayListOf<Fragment>()
        val fragment1 = VLeftFragment.newInstance(1)
        val fragment2 = VRightFragment.newInstance(2)
        fragments.add(fragment1)
        fragments.add(fragment2)
        val adapter = PagerFragmentAdapter<Fragment>(childFragmentManager, ArrayList())
        adapter.setFragments(fragments)
        custom_viewpager.adapter = adapter
        custom_viewpager.currentItem = 1
        custom_viewpager.setOnSideListener(object : CustomScrollViewPager.OnSideListener {
            override fun onRightSide() {
                drawer_layout.openDrawer(GravityCompat.END)
            }

        })
        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
                EventBus.getDefault().post(EventMessage(2))
            }

            override fun onDrawerOpened(drawerView: View) {
                val activity = activity as ViewPager2Activity
                if (position == activity.getCurPosition() && !activity.isFakeDragging()) {
                    EventBus.getDefault().post(EventMessage(1))
                }
            }

        })
    }

    override fun initLazyData() {
        val last = (1..100).shuffled().last()
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

}