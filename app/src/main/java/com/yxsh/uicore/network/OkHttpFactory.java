package com.yxsh.uicore.network;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.yxsh.uibase.application.Core;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpFactory {

    public static OkHttpClient.Builder createBuilder() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new ChuckInterceptor(Core.Companion.getContext()))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .addInterceptor(new BanAccountInterceptor())
//                .addInterceptor(new ConvertInterceptor())
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS);
    }
}
