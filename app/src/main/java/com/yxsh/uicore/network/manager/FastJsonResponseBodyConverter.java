package com.yxsh.uicore.network.manager;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * <b>类名称：</b> FastJsonResponseBodyConverter <br/>
 * <b>类描述：</b> ResponseBody转换器<br/>
 * <b>创建人：</b> Lincoln <br/>
 * <b>修改人：</b> Lincoln <br/>
 * <b>修改时间：</b> 2016年03月08日 下午3:58<br/>
 * <b>修改备注：</b> <br/>
 *
 * @version 1.0.0 <br/>
 */
class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /*
     * 转换方法
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        try {
            JSONObject object = new JSONObject(tempStr);
            int sign = 0;
            if (object.has("status")) {
                sign = object.optInt("status");
            }
            String strMsg = "";
            if (object.has("message")) {
                strMsg = object.optString("message", "");
            }
            if (sign == ResModel.SUCCESS_CODE) {
                T t = JSON.parseObject(tempStr, type);
                if (t instanceof ResModel) {
                    ((ResModel) t).setMessage(strMsg);
                }
                return t;
            } else {
                if (sign == 7) {
                    //注销账号
                }
                if (sign == 919) {
                }
                throw new ApiException(tempStr, sign);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
