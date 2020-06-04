package com.guuidea.towersdk.fragment;

import com.google.gson.JsonObject;
import com.guuidea.towersdk.net.CallBackUtil;
import com.guuidea.towersdk.net.Constants;
import com.guuidea.towersdk.net.HeaderManager;
import com.guuidea.towersdk.net.UrlHttpUtil;
import com.guuidea.towersdk.utils.Sha;

import java.util.HashMap;

public class LoginViewModel {

    private static final String TAG = "LoginViewModel";


    public void getEmailCode(String email, CallBackUtil backUtil) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        UrlHttpUtil.postJson(Constants.getEmailCodeUrl, jsonObject.toString()
                , HeaderManager.makeHeader(), backUtil);
    }

    public void getPhoneCode(String areaCode, String account, CallBackUtil backUtil) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaCode", areaCode);
        jsonObject.addProperty("phone", account);
        UrlHttpUtil.postJson(Constants.getPhoneCodeUrl, jsonObject.toString()
                , HeaderManager.makeHeader(), backUtil);
    }

    public void loginWithEmailCode(String email, String code, CallBackUtil backUtil) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("code", code);
        UrlHttpUtil.post(Constants.loginWithEmailCode, params
                , HeaderManager.makeHeader(), backUtil);
    }

    public void loginWithPhoneCode(String areaCode, String phone, String code, CallBackUtil backUtil) {
        HashMap<String, String> params = new HashMap<>();
        params.put("areaCode", areaCode);
        params.put("phone", phone);
        params.put("code", code);
        UrlHttpUtil.post(Constants.loginWithPhoneCode, params
                , HeaderManager.makeHeader(), backUtil);
    }

    public void loginWithEmailPwd(String email, String password, CallBackUtil backUtil) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", Sha.getSHA256(password));
        UrlHttpUtil.post(Constants.loginWithEmailPwd, params
                , HeaderManager.makeHeader(), backUtil);
    }

    public void loginWithPhonePwd(String areaCode, String phone, String password, CallBackUtil backUtil) {
        HashMap<String, String> params = new HashMap<>();
        params.put("areaCode", areaCode);
        params.put("phone", phone);
        params.put("password", Sha.getSHA256(password));
        UrlHttpUtil.post(Constants.loginWithEmailPwd, params
                , HeaderManager.makeHeader(), backUtil);
    }


}
