package com.yxsh.uibase.uicore.inner

import com.yxsh.uibase.net.CommonResponse


/**
 * @author novice
 */
interface INetView {

    fun launch(block: suspend () -> Unit, failed: suspend (Int, String?) -> Unit)

    fun <T> netLaunch(block: suspend () -> CommonResponse<T>, success: (msg: String?, d: T?) -> Unit, failed: (Int, String?, d: T?) -> Unit)

    fun ioLaunch(block: suspend () -> Unit, failed: suspend (Int, String?) -> Unit)

    fun <T> ioNetLaunch(block: suspend () -> CommonResponse<T>, success: (msg: String?, d: T?) -> Unit, failed: (Int, String?, d: T?) -> Unit)

}