package com.kuwan.lib.base;

import com.doule.base_lib.manager.ApplicationManager;
import com.doule.lib_net.interceptor.BanAccountInterceptor;
import com.doule.lib_net.interceptor.ConvertInterceptor;
import com.doule.lib_net.interceptor.HeaderInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpFactory {

    public static OkHttpClient.Builder createBuilder() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new ChuckInterceptor(ApplicationManager.getApplication()))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new BanAccountInterceptor())
                .addInterceptor(new ConvertInterceptor())
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS);
    }
}
