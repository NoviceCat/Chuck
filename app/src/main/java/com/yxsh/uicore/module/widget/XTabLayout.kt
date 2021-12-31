package com.yxsh.uicore.module.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.yxsh.uibase.widget.MsgView
import com.yxsh.uicore.R
import kotlinx.android.synthetic.main.view_main_tab.view.*
import com.yxsh.uicore.inner.BaseInner

/**
 * @author novice
 */
class XTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private lateinit var homeIcon: AppCompatImageView
    private lateinit var homeName: AppCompatTextView

    private lateinit var videoIcon: AppCompatImageView
    private lateinit var videoName: AppCompatTextView

    private lateinit var msgIcon: AppCompatImageView
    private lateinit var msgName: AppCompatTextView

    private lateinit var myIcon: AppCompatImageView
    private lateinit var myName: AppCompatTextView

    private lateinit var callback: (Int) -> Unit
    private val badges = SparseArray<TextView>()

    init {
        initUI()
    }

    private fun initUI() {
        setBackgroundColor(Color.WHITE)
        LayoutInflater.from(context).inflate(R.layout.view_main_tab, this, true)
        homeName = tab_home.findViewById(R.id.name)
        homeIcon = tab_home.findViewById(R.id.icon)
        tab_home.setOnClickListener(this)

        videoName = tab_video.findViewById(R.id.name)
        videoIcon = tab_video.findViewById(R.id.icon)
        tab_video.setOnClickListener(this)

        msgName = tab_msg.findViewById(R.id.name)
        msgIcon = tab_msg.findViewById(R.id.icon)
        tab_msg.setOnClickListener(this)

        myName = tab_my.findViewById(R.id.name)
        myIcon = tab_my.findViewById(R.id.icon)
        tab_my.setOnClickListener(this)
    }

    fun initTab(callback: (Int) -> Unit) {
        this.callback = callback
        initConfig(
            BaseInner.TabIndex.HOME,
            BaseInner.TabIndex.VIDEO,
            BaseInner.TabIndex.MSG,
            BaseInner.TabIndex.MINE
        )
        tabSelected(BaseInner.TabIndex.HOME)
    }

    private fun initConfig(@BaseInner.TabIndex vararg tabIndex: Int) {
        for (tab in tabIndex) {
            changeTabUnclick(tab, true)
        }
    }

    /**
     * @param tabIndex 切换tab位置
     */
    private fun tabSelected(@BaseInner.TabIndex tabIndex: Int) {
        for (pos in 0 until badges.size()) {
            val key = badges.keyAt(pos)
            if (key == tabIndex) {
                changeTabClick(key)
            } else {
                changeTabUnclick(key, false)
            }
        }
    }

    private fun changeTabUnclick(@BaseInner.TabIndex tab: Int, isInitConfig: Boolean) {
        changeTabStatus(tab, false, isInitConfig)
    }

    private fun changeTabClick(@BaseInner.TabIndex tab: Int) {
        changeTabStatus(tab, true, false)
    }

    private fun changeTabStatus(
        @BaseInner.TabIndex tab: Int,
        isClick: Boolean,
        isInitConfig: Boolean
    ) {
        if (tab == BaseInner.TabIndex.HOME) {
            if (isClick) {
                localClickConfig(
                    homeIcon,
                    homeName,
                    R.drawable.icon_tab_home,
                    R.string.title_index_home
                )
            } else {
                localUnclickConfig(
                    homeIcon,
                    homeName,
                    R.drawable.icon_tab_home,
                    R.string.title_index_home
                )
            }
            badges.put(tab, homeName)
        } else if (tab == BaseInner.TabIndex.VIDEO) {
            if (isClick) {
                localClickConfig(
                    videoIcon,
                    videoName,
                    R.drawable.icon_tab_home,
                    R.string.title_index_video
                )
            } else {
                localUnclickConfig(
                    videoIcon,
                    videoName,
                    R.drawable.icon_tab_home,
                    R.string.title_index_video
                )
            }
            badges.put(tab, videoName)
        } else if (tab == BaseInner.TabIndex.MSG) {
            if (isClick) {
                localClickConfig(
                    msgIcon,
                    msgName,
                    R.drawable.icon_tab_home,
                    R.string.title_index_msg
                )
            } else {
                localUnclickConfig(
                    msgIcon,
                    msgName,
                    R.drawable.icon_tab_home,
                    R.string.title_index_msg
                )
            }
            badges.put(tab, msgName)
        } else if (tab == BaseInner.TabIndex.MINE) {
            if (isClick) {
                localClickConfig(
                    myIcon,
                    myName,
                    R.drawable.icon_tab_home,
                    R.string.title_index_my
                )
            } else {
                localUnclickConfig(
                    myIcon,
                    myName,
                    R.drawable.icon_tab_home,
                    R.string.title_index_my
                )
            }
            badges.put(tab, myName)
        }
        if (isClick) {
            callback.invoke(tab)
        }
    }

    private fun localUnclickConfig(
        icon: AppCompatImageView,
        name: AppCompatTextView,
        iconRes: Int,
        text: Int
    ) {
        icon.setImageResource(iconRes)
        name.setText(text)
        name.setTextColor(ContextCompat.getColor(context, R.color.tab_textcolor_unfocus))
    }

    private fun localClickConfig(
        icon: AppCompatImageView,
        name: AppCompatTextView,
        iconRes: Int,
        text: Int
    ) {
        icon.setImageResource(iconRes)
        name.setText(text)
        name.setTextColor(ContextCompat.getColor(context, R.color.tab_textcolor_focus))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tab_home -> {
                tabSelected(BaseInner.TabIndex.HOME)
            }
            R.id.tab_video -> {
                tabSelected(BaseInner.TabIndex.VIDEO)
            }
            R.id.tab_msg -> {
                tabSelected(BaseInner.TabIndex.MSG)
            }
            R.id.tab_my -> {
                tabSelected(BaseInner.TabIndex.MINE)
            }
        }
    }


}