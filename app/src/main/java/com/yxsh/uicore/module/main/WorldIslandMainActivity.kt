package com.yxsh.uicore.module.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.mob.videosdk.DrawVideoFragment
import com.mob.videosdk.VideoSdk
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uicore.R
import com.yxsh.uicore.inner.BaseInner
import com.yxsh.uicore.util.DefaultViewModel
import kotlinx.android.synthetic.main.activity_main_world_island.*

/**
 * @Author : novice
 * @Date : 2021/12/31
 * @Desc : world island
 */
class WorldIslandMainActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, WorldIslandMainActivity::class.java))
        }
    }

    private lateinit var tabHomeFragment: MainHomeFragment
    private lateinit var tabVideoFragment: DrawVideoFragment
    private lateinit var tabMsgFragment: MainMessageFragment
    private lateinit var tabMineFragment: MainMineFragment

    private var curFragment: Fragment? = null

    override fun enabledVisibleToolBar(): Boolean {
        return false
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main_world_island
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        initTablayout()
    }

    private fun initTablayout() {
        tabHomeFragment = MainHomeFragment.newInstance()
        tabVideoFragment = DrawVideoFragment.newInstance()
        tabMsgFragment = MainMessageFragment.newInstance()
        tabMineFragment = MainMineFragment.newInstance()
        tablayout.initTab(callback = {
            tablayout.tag = it
            val fragment = getFragment(it)
            when (it) {
                BaseInner.TabIndex.HOME -> {
                }
                BaseInner.TabIndex.VIDEO -> {
                }
                BaseInner.TabIndex.MSG -> {
                }
                BaseInner.TabIndex.MINE -> {
                }
            }
            changeFragment(it)
        })

        tabVideoFragment.setVideoListener(object : VideoSdk.VideoListener {
            override fun onVideoShow(id: String, videoType: Int) { // 视频切换展示
                LogUtils.d("视频切换展示")
            }

            override fun onVideoStart(id: String, videoType: Int) { // 视频播放开始
                LogUtils.d("视频播放开始")
            }

            override fun onVideoPause(id: String, videoType: Int) { // 视频播放暂停
                LogUtils.d("视频播放暂停")
            }

            override fun onVideoResume(id: String, videoType: Int) { // 视频播放恢复
                LogUtils.d("视频播放恢复")
            }

            override fun onVideoComplete(id: String, videoType: Int) { // 视频播放完成
                LogUtils.d("视频播放完成")
            }

            override fun onVideoError(id: String, videoType: Int) { // 视频播放出错
                LogUtils.d("视频播放出错")
            }
        })
        tabVideoFragment.setOnLikeClickListener { id, videoType, like -> // 点赞或取消点赞
            true
        }

        tabVideoFragment.setOnShareClickListener { id, videoType, videoUrl, author, title -> // 视频分享监听
            true
        }
    }

    private fun getFragment(@BaseInner.TabIndex tabIndex: Int): Fragment? {
        when (tabIndex) {
            BaseInner.TabIndex.HOME -> return tabHomeFragment
            BaseInner.TabIndex.VIDEO -> return tabVideoFragment
            BaseInner.TabIndex.MSG -> return tabMsgFragment
            BaseInner.TabIndex.MINE -> return tabMineFragment
        }
        return null
    }

    private fun changeFragment(tabIndex: Int) {
        val fragment = getFragment(tabIndex) ?: return
        if (curFragment == fragment) {
            return
        }
        val newFragmentTag = fragment::class.java.simpleName
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        if (curFragment != null && !curFragment!!.isHidden) {
            ft.hide(curFragment!!)
        }
        val fragmentByTag = fragmentManager.findFragmentByTag(newFragmentTag)
        if (fragmentByTag == null) {
            if (!fragment.isAdded) {
                ft.add(R.id.container_main, fragment, newFragmentTag)
            }
        } else {
            ft.show(fragmentByTag)
        }
        ft.commitAllowingStateLoss()
        curFragment = fragment
    }

}