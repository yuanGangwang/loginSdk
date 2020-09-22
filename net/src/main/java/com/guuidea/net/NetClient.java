package com.guuidea.net;

import android.text.TextUtils;

import java.util.Map;

/**
 * NetClient 单例：用以保证appkey的复制保存
 */
public class NetClient {

    private NetClient() {

    }

    public static NetClient getInstance() {
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder {
        private static NetClient INSTANCE = new NetClient();
    }

    private String url;
    private String appkey;
    private Map<String, String> paramsMap;
    private String jsonParams;
    private Map<String, String> headerMap;
    private CallBackUtil callBack;

    public void appKey(String appkey) {
        this.appkey = appkey;
    }

    public NetClient url(String url) {
        this.url = url;
        return this;
    }

    public NetClient paramsMap(Map<String, String> paramsMap) {
        if (!TextUtils.isEmpty(jsonParams)) {
            throw new IllegalArgumentException("jsonParams has been called");
        }
        this.paramsMap = paramsMap;
        return this;
    }

    public NetClient jsonParams(String jsonParams) {
        if (paramsMap != null) {
            throw new IllegalArgumentException("paramsMap has been called");
        }
        this.jsonParams = jsonParams;
        return this;
    }

    public NetClient headerMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
        return this;
    }

    public NetClient callback(CallBackUtil callBack) {
        this.callBack = callBack;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public String getAppkey() {
        return appkey;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public String getJsonParams() {
        return jsonParams;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void post() {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url can't be empty");
        }

        if (TextUtils.isEmpty(appkey)) {
            throw new IllegalArgumentException("appKey can't be empty");
        }

        if (!TextUtils.isEmpty(jsonParams)) {
            UrlHttpUtil.postJson(url, jsonParams, headerMap == null ? null : headerMap, callBack);
        } else {
            UrlHttpUtil.post(url, paramsMap == null ? null : paramsMap, headerMap == null ? null : headerMap, callBack);
        }
    }

    public void get() {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url can't be empty");
        }

        if (TextUtils.isEmpty(appkey)) {
            throw new RuntimeException("appKey can't be empty");
        }

        UrlHttpUtil.get(url, paramsMap == null ? null : paramsMap, headerMap == null ? null : headerMap, callBack);

    }
}
