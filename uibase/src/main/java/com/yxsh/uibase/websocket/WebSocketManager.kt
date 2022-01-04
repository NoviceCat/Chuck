package com.yxsh.uibase.websocket

import android.os.Handler
import android.os.Looper
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.yxsh.uibase.net.HttpsUtils
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * @author novice
 *
 */
class WebSocketManager(wsUrl: String) : IWebsocketManager {

    private val TAG = this::class.java.simpleName
    private val wsUrl: String
    private val outTime: Long = 15L
    private val reconnectInterval = 3000L
    private var mWebSocket: WebSocket? = null
    private var mOkHttpClient: OkHttpClient? = null
    private var mRequest: Request? = null
    private var mCurrentStatus: Int = WebSocketStatus.DISCONNECTED //websocket连接状态
    private var isNeedReconnect = true //是否需要断线自动重连
    private var isManualClose = false //是否为手动关闭websocket连接
    private var webSocketStatusListener: WebSocketStatusListener? = null
    private var mLock: Lock? = null
    private var wsMainHandler = Handler(Looper.getMainLooper())
    private var reconnectCount = 0 //重连次数
    private var reconnectRunnable = Runnable {
        webSocketStatusListener?.onReconnecting()
        buildConnect()
    }

    init {
        this.wsUrl = wsUrl
        mLock = ReentrantLock()
    }

    private val mWebSocketListener: WebSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            mWebSocket = webSocket
            setCurrentStatus(WebSocketStatus.CONNECTED)
            connected()
            if (webSocketStatusListener != null) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    wsMainHandler.post { webSocketStatusListener!!.onOpen(response) }
                } else {
                    webSocketStatusListener!!.onOpen(response)
                }
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            if (webSocketStatusListener != null) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    wsMainHandler.post { webSocketStatusListener!!.onMessage(bytes) }
                } else {
                    webSocketStatusListener!!.onMessage(bytes)
                }
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            if (webSocketStatusListener != null) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    wsMainHandler.post { webSocketStatusListener!!.onMessage(text) }
                } else {
                    webSocketStatusListener!!.onMessage(text)
                }
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            if (webSocketStatusListener != null) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    wsMainHandler.post {
                        LogUtils.e(TAG, "[onClosing！！！！]")
                        webSocketStatusListener!!.onClosing(code, reason)
                    }
                } else {
                    LogUtils.e(TAG, "[onClosing！！！！]")
                    webSocketStatusListener!!.onClosing(code, reason)
                }
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            if (webSocketStatusListener != null) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    wsMainHandler.post {
                        LogUtils.e(TAG, "[走的onClosed！！！！]")
                        webSocketStatusListener!!.onClosed(code, reason)
                    }
                } else {
                    LogUtils.e(TAG, "[走的onClosed！！！！]")
                    webSocketStatusListener!!.onClosed(code, reason)
                }
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            try {
                tryReconnect()
                if (webSocketStatusListener != null) {
                    LogUtils.e(TAG, "[走的链接失败这里！！！！！！！！！！！！！！！！]")
                    if (Looper.myLooper() != Looper.getMainLooper()) {
                        wsMainHandler.post {
                            webSocketStatusListener!!.onFailure(t, response)
                            LogUtils.e(TAG, "[wsMainHandler。post里面]")
                        }
                    } else {
                        webSocketStatusListener!!.onFailure(t, response)
                        LogUtils.e(TAG, "[wsMainHandler。post外面----------------------------------]")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                LogUtils.e(TAG, "WebSocketListener catch message:${e.message}")
            }
        }
    }

    private fun initWebSocket() {
        if (mOkHttpClient == null) {
            val sslParams = HttpsUtils.getSslSocketFactory()
            mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(outTime, TimeUnit.SECONDS)
                .readTimeout(outTime, TimeUnit.SECONDS)
                .writeTimeout(outTime, TimeUnit.SECONDS)
                .pingInterval(outTime, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager)
                .build()
        }
        if (mRequest == null) {
            mRequest = Request.Builder()
                .url(wsUrl)
                .build()
        }
//        mOkHttpClient!!.dispatcher.cancelAll()
        try {
            mLock!!.lockInterruptibly()
            try {
                mOkHttpClient!!.newWebSocket(mRequest!!, mWebSocketListener)
            } finally {
                mLock!!.unlock()
            }
        } catch (e: InterruptedException) {
            LogUtils.e(TAG, "initWebSocket catch message:${e.message}")
        }
    }

    override fun getWebSocket(): WebSocket? {
        return mWebSocket
    }


    fun setWsStatusListener(webSocketStatusListener: WebSocketStatusListener?) {
        this.webSocketStatusListener = webSocketStatusListener
    }

    @Synchronized
    override fun isConnected(): Boolean {
        return mCurrentStatus == WebSocketStatus.CONNECTED
    }

    @Synchronized
    override fun getCurrentStatus(): Int {
        return mCurrentStatus
    }

    @Synchronized
    override fun setCurrentStatus(currentStatus: Int) {
        mCurrentStatus = currentStatus
    }

    override fun startConnect() {
        isManualClose = false
        buildConnect()
    }

    override fun stopConnect() {
        isManualClose = true
        disconnect()
    }

    private fun tryReconnect() {
        if (!isNeedReconnect or isManualClose) {
            return
        }
        if (!NetworkUtils.isConnected()) {
            setCurrentStatus(WebSocketStatus.DISCONNECTED)
            LogUtils.e(TAG, "[请您检查网络，未连接]")
        }
        setCurrentStatus(WebSocketStatus.RECONNECT)
        wsMainHandler.postDelayed(reconnectRunnable, reconnectInterval)
        reconnectCount++
        webSocketStatusListener?.onReConnectCount(reconnectCount)
    }

    private fun cancelReconnect() {
        wsMainHandler.removeCallbacks(reconnectRunnable)
        reconnectCount = 0
    }

    private fun connected() {
        cancelReconnect()
    }

    private fun disconnect() {
        if (mCurrentStatus == WebSocketStatus.DISCONNECTED) {
            return
        }
        cancelReconnect()
//        if (mOkHttpClient != null) {
//            mOkHttpClient!!.dispatcher.cancelAll()
//        }
        if (mWebSocket != null) {
            val isClosed = mWebSocket!!.close(WebSocketStatus.Code.NORMAL_CLOSE, WebSocketStatus.Tip.NORMAL_CLOSE)
            //非正常关闭连接
            if (!isClosed) {
                webSocketStatusListener?.onClosed(WebSocketStatus.Code.ABNORMAL_CLOSE, WebSocketStatus.Tip.ABNORMAL_CLOSE)
            }
        }
        setCurrentStatus(WebSocketStatus.DISCONNECTED)
    }

    @Synchronized
    private fun buildConnect() {
        if (!NetworkUtils.isConnected()) {
            setCurrentStatus(WebSocketStatus.DISCONNECTED)
        }
        when (getCurrentStatus()) {
            WebSocketStatus.CONNECTED, WebSocketStatus.CONNECTING -> {
            }
            else -> {
                setCurrentStatus(WebSocketStatus.CONNECTING)
                initWebSocket()
            }
        }
    }

    override fun sendMessage(msg: String?): Boolean {
        return send(msg)
    }

    override fun sendMessage(byteString: ByteString?): Boolean {
        return send(byteString)
    }


    private fun send(msg: Any?): Boolean {
        var isSend = false
        if (mWebSocket != null && mCurrentStatus == WebSocketStatus.CONNECTED) {
            if (msg is String) {
                isSend = mWebSocket!!.send(msg)
            } else if (msg is ByteString) {
                isSend = mWebSocket!!.send(msg)
            }
            //发送消息失败，尝试重连
            if (!isSend) {
                tryReconnect()
            }
        }
        return isSend
    }


}