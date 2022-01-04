package com.zxtx.sjd.util

import kotlin.random.Random

/**
 * @Author : novice
 * @Date : 2022/1/4
 * @Desc : 本地工具类
 */
class LocalUtil {

    companion object {

        fun getRandomStr():String {
            return "" + System.currentTimeMillis() + "" + Random.nextInt(1, 1000)
        }

    }
}