package com.yxsh.uibase.websocket

import okhttp3.Response
import okio.ByteString

/**
 * @author novice
 *
 */
interface WebSocketStatusListener {

    fun onOpen(response: Response?)

    fun onMessage(text: String?)

    fun onMessage(bytes: ByteString?)

    fun onReconnecting()

    fun onReConnectCount(count: Int)

    fun onClosing(code: Int, reason: String?)

    fun onClosed(code: Int, reason: String?)

    fun onFailure(t: Throwable?, response: Response?)

}