package com.yxsh.uibase.utils

import android.content.Context
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator


/**
 * author novic
 * date 2019/11/8
 */
object SmartRefreshLayoutUtils {

    fun init() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
            override fun createRefreshHeader(
                context: Context,
                layout: RefreshLayout
            ): RefreshHeader {
                layout.setPrimaryColorsId(
                    android.R.color.white,
                    android.R.color.white
                )//全局设置主题颜色
                return ClassicsHeader(context).setTimeFormat(DynamicTimeFormat("更新于 %s"))//指定为经典Header，默认是 贝塞尔雷达Header
            }
        })
    }

}