package com.yxsh.uibase.net

/**
 * @author novice
 */
fun <T> CommonResponse<T>.process(success: (msg: String?, d: T?) -> Unit, failed: (status: Int, msg: String?, d: T?) -> Unit) {
    try {
        if (status == HttpNetCode.SUCCESS) {
            success(message, data)
        } else {
            failed(status, message, data)
        }
    } catch (e: Throwable) {
        failed(HttpNetCode.DATA_ERROR, e.message, null)
    }
}



