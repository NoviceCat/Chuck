package com.yxsh.uibase.net

import com.yxsh.uibase.keep.KeepClass

/**
 * @author novice
 */
class CommonResponse<T> : KeepClass {
    var status: Int = 0
    var message: String? = null
    var data: T? = null
}