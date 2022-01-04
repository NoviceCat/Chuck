package com.zxtx.sjd.network.manager;

/**
 * 接口返回错误
 */
public class ApiException extends RuntimeException {

    public static int S_Null_Data_Error = 1001;

    private int errorCode = 0;
    private String errorMsg;


    public ApiException(String errorMsg, int errorCode) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public ApiException() {

    }

    public ApiException(int errorCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}


