package com.guuidea.im.config;

import android.content.Context;
import android.text.TextUtils;

public class FLyIMClient {

    private static Context mContext;
    private static SDKOptions options;

    public static void config(Context context,SDKOptions sdkOptions){
        mContext = context;
        options = sdkOptions;
    }

    public static String getAppKey(){
        return options.getAppKey();
    }

    public static String getAuthToken() throws Throwable {
        if (options==null||TextUtils.isEmpty(options.getAuthToken())){
            throw new Throwable();
        }
        return options.getAuthToken();
    }

}
