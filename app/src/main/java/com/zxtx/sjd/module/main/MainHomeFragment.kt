package com.zxtx.sjd.module.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.doule.base_lib.utils.extension.safe
import com.zxtx.uibase.dialog.CheckPicDialog
import com.zxtx.uibase.glide.GlideUtils
import com.zxtx.uibase.uicore.ui.BaseFragment
import com.zxtx.uibase.utils.DoubleClickUtils
import com.zxtx.sjd.R
import com.zxtx.sjd.bean.user.LoginCheckPicBean
import com.zxtx.sjd.util.zxing.ScanActivity
import com.zxtx.sjd.network.manager.NetObserver
import com.zxtx.sjd.network.login.UserHttp
import com.zxtx.sjd.util.DefaultViewModel
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.indicator.base.IIndicator
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zxtx.sjd.demo.TouchActivity
import kotlinx.android.synthetic.main.frg_main_home.*

/**
 * @Author : novice
 * @Date : 2021/12/31
 * @Desc : home
 */
class MainHomeFragment : BaseFragment<DefaultViewModel>(), View.OnClickListener {

    companion object {
        fun newInstance(): MainHomeFragment {
            return MainHomeFragment().apply {
                val bundle = Bundle()
                arguments = bundle
            }
        }
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.frg_main_home
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        initCoverBanner(arrayListOf("", "", "", "", ""))
        iv_scan.setOnClickListener(this)
        tv_click.setOnClickListener(this)
    }

    private fun initCoverBanner(list: List<String>) {
        val dataList = UserInfoCoverBannerAdapter.strings2BannerEntityList(list)
        if (cover_banner.adapter == null) {
            val dotSize = SizeUtils.dp2px(4f)
            val dotMargin = SizeUtils.dp2px(3f)
            (cover_banner as BannerViewPager<UserInfoCoverBannerAdapter.CoverBannerEntity>).setIndicatorGravity(
                IndicatorGravity.END
            )
                .setAdapter(UserInfoCoverBannerAdapter())
                .setAutoPlay(true)
                .setCanLoop(true)
                .setInterval(5000)
                .setScrollDuration(500)
                .setIndicatorVisibility(View.GONE)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorView(getVectorDrawableIndicator())
                .setOnPageClickListener { _, position ->
                    ToastUtils.showShort("点击 == $position")
                }
                .create(dataList)
        } else {
            (cover_banner as BannerViewPager<UserInfoCoverBannerAdapter.CoverBannerEntity>).refreshData(
                dataList
            )
        }
    }

    private fun getVectorDrawableIndicator(): IIndicator {
        val dp6 = resources.getDimensionPixelOffset(R.dimen.dp_6)
        return indicator_view
            .setIndicatorGap(resources.getDimensionPixelOffset(R.dimen.dp_5))
            .setIndicatorDrawable(
                R.drawable.bg_main_banner_normal,
                R.drawable.bg_main_banner_check
            )
            .setIndicatorSize(dp6, dp6, resources.getDimensionPixelOffset(R.dimen.dp_13), dp6)
    }

    class UserInfoCoverBannerAdapter
        : BaseBannerAdapter<UserInfoCoverBannerAdapter.CoverBannerEntity>() {
        override fun bindData(
            holder: BaseViewHolder<CoverBannerEntity>,
            data: CoverBannerEntity,
            position: Int,
            pageSize: Int
        ) {
            holder.itemView.tag = data.url
            GlideUtils.load(
                holder.itemView.context,
                R.drawable.img_beijing,
                holder.itemView as ImageView
            )
        }

        override fun getLayoutId(viewType: Int) = R.layout.item_user_info_cover

        companion object {
            fun strings2BannerEntityList(list: List<String>?): List<CoverBannerEntity> {
                if (list == null || list.isEmpty()) {
                    //显示默认的图片
                    return mutableListOf(CoverBannerEntity(true))
                }
                return list?.map { CoverBannerEntity(false, it) }.safe().toList()
            }

        }

        class CoverBannerEntity {
            //是否显示默认的图片
            var isDefault = false
            var url = ""

            constructor(isDefault: Boolean, url: String = "") {
                this.isDefault = isDefault
                this.url = url
            }
        }
    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.iv_scan -> {// 点击 扫描
//                ScanActivity.start(requireContext())
                TouchActivity.start(requireContext())
            }
            R.id.tv_click -> {// 点击 获取验证码
                if (DoubleClickUtils.instance.isInvalidClick()) return
                UserHttp.getLoginCheckPicDetail()!!
                    .subscribe(object : NetObserver<LoginCheckPicBean?>() {
                        override fun onSuccess(bean: LoginCheckPicBean?) {
                            super.onSuccess(bean)
                            CheckPicDialog(requireContext(), object : CheckPicDialog.CheckCallBack {
                                override fun onCheckSuccess() {

                                }

                                override fun onCheckFail() {
                                }

                            }).showPopupWindow()
                        }

                        override fun onError(e: Throwable) {
                            super.onError(e)
                        }
                    })
            }
        }
    }
}