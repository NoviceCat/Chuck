package com.zxtx.uibase.net

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @author novice
 */
class NetLongLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val maxSize = 3000
        if (message.length > maxSize) {
            var i = 0
            while (i < message.length) {
                if (i + maxSize < message.length)
                    Log.i("NetLog$i", message.substring(i, i + maxSize))
                else
                    Log.i("NetLog$i", message.substring(i, message.length))
                i += maxSize
            }
        } else
            Log.i("NetLog", message)
    }
}