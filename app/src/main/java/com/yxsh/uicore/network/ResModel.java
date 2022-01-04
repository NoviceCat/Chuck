package com.yxsh.uicore.network;


/**
 * 基本数据模型
 */
public class ResModel<T> {

    //data对象
    private T data;
    //返回结果
    private int status = 0;// 200为成功

    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //------------------------一些额外的信息,这些数据不参与解析与被解析-----------------------------//
    public static final int SUCCESS_CODE = 200;
    public static final int BAN_ACCOUNT_CODE = 48484;//封号
    public static final int MAINTENANCE_STATUS_CODE = 919;//维护状态

    private String content;//返回的json内容
    private boolean isFromCache;//是否从缓存读取



}
