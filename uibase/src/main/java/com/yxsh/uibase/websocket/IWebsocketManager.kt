package com.yxsh.uibase.websocket

import okhttp3.WebSocket
import okio.ByteString

/**
 * @author novice
 *
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