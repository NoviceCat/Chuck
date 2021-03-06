package com.zxtx.sjd.network.manager

import com.zxtx.sjd.network.URLConfig
import com.zxtx.sjd.network.login.UserApi

/**
 * @author novice
 */
class HttpManager {
    private var userApi: UserApi? = null

    init {
        userApi = LoadUtils.create(URLConfig.USER_API, UserApi::class.java)
    }

    companion object {
        private var mInstance: HttpManager? = null
        val instance: HttpManager?
            get() {
                if (mInstance == null) {
                    synchronized(HttpManager::class.java) {
                        if (mInstance == null) {
                            mInstance = HttpManager()
                        }
                    }
                }
                return mInstance
            }
    }

    fun getUserApi(): UserApi {
        return userApi!!
    }

    fun destroy() {
        mInstance = null
    }

}