package com.guuidea.towersdk.activity;

import android.os.Bundle;
import android.view.View;

import com.google.gson.JsonObject;
import com.guuidea.net.CallBackUtil;
import com.guuidea.net.HeaderManager;
import com.guuidea.net.NetClient;
import com.guuidea.towersdk.R;
import com.guuidea.towersdk.TowerLogin;
import com.guuidea.towersdk.net.Constants;
import com.guuidea.towersdk.utils.CheckUtils;
import com.guuidea.towersdk.utils.ToastUtil;
import com.guuidea.towersdk.weight.PwdEtView;

import java.util.Map;

public class SetPwdActivity extends BaseActivity {

    private String authToken;
    private PwdEtView pwdOne;
    private PwdEtView pwdTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pwd);

        pwdOne = findViewById(R.id.pwdOne);
        pwdTwo = findViewById(R.id.pwdTwo);

        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pwdOne.getPwd().equals(pwdTwo.getPwd())) {
                    showCommonToast(getString(R.string.pwd_not_match));
                    return;
                }
                if (CheckUtils.checkPwd(pwdOne.getPwd(), pwdTwo.getPwd())) {
                    resetPwd(pwdOne.getPwd());
                } else {
                    showCommonToast(getString(R.string.pwd_rules));
                }
            }
        });

        authToken = getIntent().getStringExtra("authToken");
    }


    private void resetPwd(String pwd) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", pwd);
        Map<String, String> header = HeaderManager.makeHeader();
        header.put("authToken",authToken);
//        UrlHttpUtil.postJson(Constants.setPwd, jsonObject.toString()
//                , header, new CallBackUtil() {
//                    @Override
//                    public void onFailure(Throwable throwable) {
//                        showCommonToast(throwable.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(JsonObject response) {
//                        ToastUtil.getInstance(SetPwdActivity.this).showCustomer(SetPwdActivity.this, R.string.set_pwd_success);
//                        finish();
//                    }
//                });

        NetClient.getInstance().url(Constants.setPwd)
                .jsonParams(jsonObject.toString())
                .headerMap(header)
                .callback(new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        showCommonToast(throwable.getMessage());
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        ToastUtil.getInstance(SetPwdActivity.this).showCustomer(SetPwdActivity.this, R.string.set_pwd_success);
                        finish();
                    }
                })
                .post();
    }

    @Override
    protected void onDestroy() {
        TowerLogin.getInstance().getLoginResult().onSuccess(authToken);
        super.onDestroy();

    }
}