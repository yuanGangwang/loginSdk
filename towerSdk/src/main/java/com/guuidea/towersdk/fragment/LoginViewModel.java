package com.guuidea.towersdk.fragment;

import com.google.gson.JsonObject;
import com.guuidea.net.CallBackUtil;
import com.guuidea.net.HeaderManager;
import com.guuidea.net.NetClient;
import com.guuidea.towersdk.net.Constants;

public class LoginViewModel {

    private static final String TAG = "LoginViewModel";

    public void getEmailCode(String email, CallBackUtil backUtil) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        NetClient.getInstance()
                .url(Constants.getEmailCodeUrl)
                .jsonParams(jsonObject.toString())
                .headerMap(HeaderManager.makeHeader())
                .callback(backUtil)
                .post();

//        UrlHttpUtil.postJson(Constants.getEmailCodeUrl, jsonObject.toString()
//                , HeaderManager.makeHeader(), backUtil);
    }

    public void getPhoneCode(String areaCode, String account, CallBackUtil backUtil) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaCode", areaCode);
        jsonObject.addProperty("phone", account);
//        UrlHttpUtil.postJson(Constants.getPhoneCodeUrl, jsonObject.toString()
//                , HeaderManager.makeHeader(), backUtil);
        NetClient.getInstance()
                .url(Constants.getPhoneCodeUrl)
                .jsonParams(jsonObject.toString())
                .headerMap(HeaderManager.makeHeader())
                .callback(backUtil)
                .post();
    }

    public void loginWithEmailCode(String email, String code, CallBackUtil backUtil) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("code", code);
//        UrlHttpUtil.postJson(Constants.loginWithEmailCode, jsonObject.toString()
//                , HeaderManager.makeHeader(), backUtil);
        NetClient.getInstance()
                .url(Constants.loginWithEmailCode)
                .jsonParams(jsonObject.toString())
                .headerMap(HeaderManager.makeHeader())
                .callback(backUtil)
                .post();
    }

    public void loginWithPhoneCode(String areaCode, String phone, String code, CallBackUtil backUtil) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaCode", areaCode);
        jsonObject.addProperty("phone", phone);
        jsonObject.addProperty("code", code);

//        UrlHttpUtil.postJson(Constants.loginWithPhoneCode, jsonObject.toString()
//                , HeaderManager.makeHeader(), backUtil);
        NetClient.getInstance()
                .url(Constants.loginWithPhoneCode)
                .jsonParams(jsonObject.toString())
                .headerMap(HeaderManager.makeHeader())
                .callback(backUtil)
                .post();
    }

    public void loginWithEmailPwd(String email, String password, CallBackUtil backUtil) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
//        jsonObject.addProperty("password", Sha.getSHA256(password));
        jsonObject.addProperty("password", (password));

//        UrlHttpUtil.postJson(Constants.loginWithEmailPwd, jsonObject.toString()
//                , HeaderManager.makeHeader(), backUtil);

        NetClient.getInstance()
                .url(Constants.loginWithEmailPwd)
                .jsonParams(jsonObject.toString())
                .headerMap(HeaderManager.makeHeader())
                .callback(backUtil)
                .post();
    }

    public void loginWithPhonePwd(String areaCode, String phone, String password, CallBackUtil backUtil) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaCode", areaCode);
        jsonObject.addProperty("phone", phone);
//        jsonObject.addProperty("password", Sha.getSHA256(password));
        jsonObject.addProperty("password", (password));
//        UrlHttpUtil.postJson(Constants.loginWithPhonePwd, jsonObject.toString()
//                , HeaderManager.makeHeader(), backUtil);

        NetClient.getInstance()
                .url(Constants.loginWithPhonePwd)
                .jsonParams(jsonObject.toString())
                .headerMap(HeaderManager.makeHeader())
                .callback(backUtil)
                .post();
    }


}
