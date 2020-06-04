package com.guuidea.towersdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class TowerLogin {

    private static volatile TowerLogin towerLogin = null;
    Context mContext;
    private String appKey;
    private LoginResult loginResult;

    public static TowerLogin getInstance() {
        if (towerLogin == null) {
            synchronized (TowerLogin.class) {
                if (towerLogin == null) {
                    towerLogin = new TowerLogin();
                }
            }
        }
        return towerLogin;
    }

    public String getAppKey() {
        return appKey;
    }

    public LoginResult getLoginResult() {
        return loginResult;
    }

    public void startLoginAuth(Context context, String appkey, LoginResult loginResult) {
        if (loginResult == null) {
            new Exception("LoginResult is null").printStackTrace();
            return;
        }
        if (context == null) {
            errorReturn("Context is null");
            return;
        }

        if (TextUtils.isEmpty(appkey)) {
            errorReturn("appkey is null");
            return;
        }

        this.loginResult = loginResult;
        this.appKey = appkey;
        this.mContext = context;
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }

    private void errorReturn(String errorMessage) {
        Throwable exception = new Throwable(errorMessage);
        loginResult.onError(exception);
        exception.printStackTrace();
    }

}
