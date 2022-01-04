package com.yxsh.uicore.network;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 消息回调信息
 * Created by LinXin on 2016/8/8 10:15.
 */
public class ResMsg {
    private String msg;
    private String tips;
    private String code;

    @JSONField(name = "msg",alternateNames = "resultMsg")
    public String getMsg() {
        return msg;
    }

    @JSONField(name = "msg",alternateNames = "resultMsg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JSONField(name = "tips")
    public String getTips() {
        return tips;
    }

    @JSONField(name = "tips")
    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
