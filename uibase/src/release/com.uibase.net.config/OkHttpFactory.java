package com.kuwan.lib.base;

import com.doule.lib_net.interceptor.BanAccountInterceptor;
import com.doule.lib_net.interceptor.ConvertInterceptor;
import com.doule.lib_net.interceptor.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpFactory {

    public static OkHttpClient.Builder createBuilder() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new BanAccountInterceptor())
                .addInterceptor(new ConvertInterceptor())
//                .addInterceptor(new ChuckInterceptor(ApplicationManager.getApplication()))
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS);
    }
}
