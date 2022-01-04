package com.yxsh.uicore.network.manager;

import android.text.TextUtils;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class NetObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        try {
            if (t instanceof String) {
                if (TextUtils.isEmpty((CharSequence) t)) {
                    onSuccess(null);
                    return;
                }
                onSuccess(t);
                return;
            }
            if (t == null) {
                onSuccess(null);
                return;
            }
            onSuccess(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSuccess(T t) {

    }

    public void onErrorMsg(int code, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ToastUtils.showShort(msg);
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.d("NetObserver -- > onError --> ",e.toString());
        try {
            if (e instanceof ApiException) {
                ApiException apiException = (ApiException) e;
                onErrorMsg(apiException.getErrorCode(), apiException.getMessage());
                return;
            }

            //出现异常优先判断是否有网络连接，无网络连接直接提示
            if (!NetworkUtils.isConnected()
                    || e instanceof HttpException
                    || e instanceof UnknownHostException
                    || e instanceof ConnectException
            ) {
                onErrorMsg(-101, "当前网络不可用");
                return;
            }
            //json 解析异常
            if (e instanceof com.alibaba.fastjson.JSONException
                    || e instanceof JSONException
                    || e instanceof ParseException) {
                onErrorMsg(-102, "数据解析异常");
                return;
            }
        } catch (Exception ex) {
            e.printStackTrace();
            onErrorMsg(-100, "当前网络不可用");
        }
    }

    @Override
    public void onComplete() {
    }
}
