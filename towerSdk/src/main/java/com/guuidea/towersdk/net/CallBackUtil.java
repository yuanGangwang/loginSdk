package com.guuidea.towersdk.net;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.JsonObject;
import com.guuidea.towersdk.TowerLogin;


public abstract class CallBackUtil {
    static Handler mMainHandler = new Handler(Looper.getMainLooper());

    void onError(final Throwable response) {

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (TowerLogin.getInstance().getLoginResult() != null) {
                    TowerLogin.getInstance().getLoginResult().onError(response);
                }
                onFailure(response);
            }
        });
    }

    void onSuccess(final JsonObject response) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                onResponse(response);
            }
        });
    }


    /**
     * 访问网络失败后被调用，执行在UI线程
     */
    public abstract void onFailure(Throwable throwable);

    /**
     * 访问网络成功后被调用，执行在UI线程
     *
     * @param response
     */
    public abstract void onResponse(JsonObject response);

}
