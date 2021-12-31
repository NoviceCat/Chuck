package com.yxsh.uibase.net

import com.yxsh.uibase.keep.KeepClass

/**
 * @author novice
 * @date 2020/6/12
 */
class CommonResponse<T> : KeepClass {
    var status: Int = 0
    var message: String? = null
    var data: T? = null
}