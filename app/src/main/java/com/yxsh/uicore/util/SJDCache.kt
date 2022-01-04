package com.yxsh.uicore.util

import com.blankj.utilcode.util.SPUtils

/**
 * @Author : novice
 * @Date : 2022/1/4
 * @Desc : 世界岛 本地缓存数据
 */
class SJDCache {

    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
        const val KEY_CARD_ID = "KEY_CARD_ID"

        fun getLoginToken():String {
            return SPUtils.getInstance().getString(KEY_TOKEN, "")
        }

        fun getCardId():String {
            return SPUtils.getInstance().getString(KEY_CARD_ID, "")
        }
    }



}