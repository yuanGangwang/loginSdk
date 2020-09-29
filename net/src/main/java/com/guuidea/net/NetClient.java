package com.guuidea.net;

import android.text.TextUtils;
import android.widget.HeterogeneousExpandableList;

import java.util.HashMap;
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
    private Map<String, String> paramsMap;
    private String jsonParams;
    private Map<String, String> headerMap;
    private CallBackUtil callBack;

    public NetClient customHeader(String key, String value) {
        if (null != headerMap) {
            headerMap.put(key, value);
        } else {
            headerMap = new HashMap<>();
            headerMap.put(key, value);
        }
        return this;
    }

    public NetClient customParam(String key, String value) {
        if (null != paramsMap) {
            paramsMap.put(key, value);
        } else {
            paramsMap = new HashMap<>();
            paramsMap.put(key, value);
        }
        return this;
    }

    public NetClient url(String url) {
        this.url = url;
        return this;
    }

    public NetClient paramsMap(Map<String, String> paramsMap) {
        if (!TextUtils.isEmpty(jsonParams)) {
            throw new IllegalArgumentException("jsonParams has been called");
        }
        if (null == paramsMap) {
            this.paramsMap = paramsMap;
        } else {
            this.paramsMap.putAll(paramsMap);
        }
        return this;
    }

    public NetClient jsonParams(String jsonParams) {
        if (null != paramsMap) {
            throw new IllegalArgumentException("paramsMap has been called");
        }
        this.jsonParams = jsonParams;
        return this;
    }

    public NetClient headerMap(Map<String, String> headerMap) {
        if (null == headerMap) {
            this.headerMap = headerMap;
        } else {
            this.headerMap.putAll(headerMap);
        }
        return this;
    }

    public NetClient callback(CallBackUtil callBack) {
        this.callBack = callBack;
        return this;
    }

    public String getUrl() {
        return url;
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

        if (!TextUtils.isEmpty(jsonParams)) {
            UrlHttpUtil.postJson(url, jsonParams, headerMap, callBack);
        } else {
            UrlHttpUtil.post(url, paramsMap, headerMap, callBack);
        }
    }

    public void get() {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url can't be empty");
        }
        UrlHttpUtil.get(url, paramsMap, headerMap, callBack);

    }
}
