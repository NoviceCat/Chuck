package com.zxtx.uibase.net

import com.zxtx.uibase.keep.KeepClass

/**
 * @author novice
 */
class CommonResponse<T> : KeepClass {
    var status: Int = 0
    var message: String? = null
    var data: T? = null
}