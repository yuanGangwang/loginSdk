package com.guuidea.towersdk.net;

import com.google.gson.JsonObject;

import java.io.InputStream;

/**
 * Created by fighting on 2017/4/24.
 */

public class RealResponse {

    public int code;

    public JsonObject response;

    public Throwable throwable;

    public RealResponse(int code, JsonObject response) {
        this.code = code;
        this.response = response;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public RealResponse(int code, Throwable throwable) {
        this.code = code;
        this.throwable = throwable;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JsonObject getResponse() {
        return response;
    }

    public void setResponse(JsonObject response) {
        this.response = response;
    }
}
