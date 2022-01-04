package com.yxsh.uicore.network;


import com.blankj.utilcode.util.LogUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;


/**
 * Created by dada on 2020/8/25.
 */
public class ResponseTransformer {

    public static <T> ObservableTransformer<ResModel<T>, T> handleResult() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends ResModel<T>>> {

        @Override
        public ObservableSource<? extends ResModel<T>> apply(Throwable e) throws Exception {
            if (e == null) {
                LogUtils.e("网络请求异常===》", "未获取到错误信息");
                return null;
            }
            if (e.getMessage() != null) {
                LogUtils.e("网络请求异常===》", "  msg==" + e.getMessage());
                return Observable.error(e);
            }
            LogUtils.e("网络请求异常===》", "  msg==" + e.toString());
            return Observable.error(e);
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<ResModel<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(ResModel<T> tResponse) throws Exception {
            int code = tResponse.getStatus();
            if (code == ResModel.SUCCESS_CODE) {
                if (tResponse.getData() == null) {
                    return (ObservableSource<T>) Observable.just("");
                }
                return Observable.just(tResponse.getData());
            } else {
                String message = tResponse.getMessage();
                return Observable.error(new ApiException(message, code));
            }
        }
    }
}

