package com.yxsh.uicore.inner

/**
 * @author huwencheng
 * @date 2020/7/1
 */
interface BaseInner {

    annotation class TabIndex {
        companion object {
            const val HOME = 1//首页
            const val VIDEO = 2//短视频
            const val MSG = 3//
            const val MINE = 4//我的
        }
    }

}