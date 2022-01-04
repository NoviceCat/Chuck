package com.zxtx.sjd.network.manager;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author novic
 * @date 2021/11/2
 * @desc OKHttp 信任所有https
 */
public class TrustAllCerts implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
