package com.yxsh.uibase.websocket

/**
 * @author novice
 *
 */
object WebSocketStatus {

    const val CONNECTING = 0
    const val CONNECTED = 1
    const val RECONNECT = 2
    const val DISCONNECTED = -1

    object Code {
        const val NORMAL_CLOSE = 1000
        const val ABNORMAL_CLOSE = 1001
    }

    object Tip {
        const val NORMAL_CLOSE = "normal_close"
        const val ABNORMAL_CLOSE = "abnormal_close"
    }

}