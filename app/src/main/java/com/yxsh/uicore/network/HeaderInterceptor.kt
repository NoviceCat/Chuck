package com.yxsh.uicore.network

import com.yxsh.uicore.util.SJDCache
import okhttp3.Interceptor
import okhttp3.Response


class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()
        newRequestBuilder.header("contentType", "application/json;charset=UTF-8")
        newRequestBuilder.header("Authorization", SJDCache.getLoginToken())
        newRequestBuilder.header("cardId", SJDCache.getCardId())
        val newRequest = newRequestBuilder.build()
        return chain.proceed(newRequest)
    }
}