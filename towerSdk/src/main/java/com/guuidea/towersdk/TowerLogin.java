package com.guuidea.towersdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import com.guuidea.net.NetClient;
import com.guuidea.towersdk.bean.AccountType;
import com.guuidea.towersdk.net.Constants;

public class TowerLogin {

    private static volatile TowerLogin towerLogin = null;
    Context mContext;
    private String appKey;
    private LoginResult loginResult;
    private AccountType loginType;

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

    public AccountType getLoginType() {
        return loginType;
    }

    public void startLoginAuth(Context context, String appkey, AccountType accountType, LoginResult loginResult) {
        if (loginResult == null) {
            new Exception("LoginResult is null").printStackTrace();
            return;
        }
        if (context == null) {
            errorReturn("Context is null");
            return;
        }

        if (TextUtils.isEmpty(appkey)) {
            errorReturn("AppKey is null");
            return;
        }
        if (accountType == null) {
            this.loginType = AccountType.ALL;
        } else {
            this.loginType = accountType;
        }

        this.loginResult = loginResult;
        this.appKey = appkey;
        this.mContext = context;

//        LanguageUtils.getInstance().setContext(context);
        //初始化，设置appkey，供后续调用
        NetClient.getInstance().customHeader("appkey", appkey);
        Constants.getInstance().debug(isDebug(context))
                .debugUrl("http://47.110.12.104:9000")
                .releaseUrl("http://api-gateway.globalneutron.com")
                .init();
        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(loginIntent);
    }

    private void errorReturn(String errorMessage) {
        Throwable exception = new Throwable(errorMessage);
        loginResult.onError(exception);
        exception.printStackTrace();
    }

    public boolean isDebug(Context context) {
        boolean isDebug = context.getApplicationInfo() != null &&
                (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        return isDebug;
    }

}
