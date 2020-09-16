package com.guuidea.towersdk.thirdapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.guuidea.towersdk.net.CallBackUtil;
import com.guuidea.towersdk.net.UrlHttpUtil;
import com.guuidea.towersdk.thirdapi.bean.UserCoinsInfo;
import com.guuidea.towersdk.thirdapi.bean.UserInfoBean;

import java.util.HashMap;
import java.util.Map;

public class CoinUserUtils {

    static CoinUserUtils coinUserUtils;
    private static Map<String, String> header = new HashMap<>();

    public static CoinUserUtils getInstance() {
        if (coinUserUtils == null) {
            coinUserUtils = new CoinUserUtils();
        }

        return coinUserUtils;
    }

    public static void getUserInfo(final CoinUserInfoCallBack callBack) {
        UrlHttpUtil.postJson(ThirdApiConstant.getUserInfo, null
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 200) {
                            UserInfoBean userInfoBean = new Gson().fromJson(response.getAsJsonObject("data"), UserInfoBean.class);
                            callBack.userInfoCallBack(userInfoBean);
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });
    }

    /**
     * userName	String	能	用户名
     * avatarAddress	String	能	用户头像地址
     * mobilePhone	String	能	手机号
     * email	String	能	邮箱
     * paypal	String	能	PayPal账号
     */
    public static void updateUserInfo(JsonObject json, final CoinUserInfoCallBack callBack) {
        UrlHttpUtil.postJson(ThirdApiConstant.updateUserInfo, json.toString()
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 200) {
                            UserInfoBean userInfoBean = new Gson().fromJson(response.getAsJsonObject("data"), UserInfoBean.class);
                            callBack.userInfoCallBack(userInfoBean);
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });

    }

    public static void getUserCoin(final CoinUserCoinCallBack callBack) {
        UrlHttpUtil.postJson(ThirdApiConstant.getUserCoin, null
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 200) {
                            UserCoinsInfo coinsInfo = new Gson().fromJson(response.getAsJsonObject("data"), UserCoinsInfo.class);
                            callBack.userCoinCallBack(coinsInfo);
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });
    }

    public static void inputInviteCOde(String code, final CoinUserInviteCallBack callBack) {
        JsonObject json = new JsonObject();
        json.addProperty("invitationCode", code);
        UrlHttpUtil.postJson(ThirdApiConstant.inputInviteCOde, json.toString()
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 200) {
                            callBack.userInviteCallBack();
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });
    }

    public CoinUserUtils addHeader(String key, String value) {
        header.put(key, value);
        return coinUserUtils;
    }


   public interface CoinUserInfoCallBack {
        void userInfoCallBack(UserInfoBean userInfoBean);

        void onFail(Throwable throwable);
    }


    public  interface CoinUserCoinCallBack {
        void userCoinCallBack(UserCoinsInfo userCoinsInfo);

        void onFail(Throwable throwable);
    }

    public interface CoinUserInviteCallBack {
        void userInviteCallBack();

        void onFail(Throwable throwable);
    }
}
