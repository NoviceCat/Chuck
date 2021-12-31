package com.yxsh.uibase.websocket

import okhttp3.WebSocket
import okio.ByteString

/**
 * @author novice
 * @date 2020/6/29
 */
interface IWebsocketManager {

    fun getWebSocket(): WebSocket?

    fun startConnect()

    fun stopConnect()

    fun isConnected(): Boolean

    fun getCurrentStatus(): Int

    fun setCurrentStatus(currentStatus: Int)

    fun sendMessage(msg: String?): Boolean

    fun sendMessage(byteString: ByteString?): Boolean

}