package com.guuidea.towersdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;
import com.guuidea.towersdk.activity.BaseActivity;
import com.guuidea.towersdk.activity.SetPwdActivity;
import com.guuidea.towersdk.fragment.EmailLoginFragment;
import com.guuidea.towersdk.fragment.LoginBaseFragment;
import com.guuidea.towersdk.fragment.PhoneLoginFragment;
import com.guuidea.towersdk.utils.CheckUtils;
import com.guuidea.towersdk.utils.ToastUtil;
import com.guuidea.towersdk.bean.AccountType;
import com.guuidea.towersdk.weight.StateButton;

public class LoginActivity extends BaseActivity {


    AccountType localType = AccountType.Phone;
    PhoneLoginFragment phoneEmail;
    EmailLoginFragment emailFragment;
    boolean isLoginSuccess = false;
    private TextView switchLoginTv;
    private StateButton loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        initView();
        chooseAccountViewShow();
    }

    private void chooseAccountViewShow() {
        if (TowerLogin.getInstance().getLoginType().equals(AccountType.Email)) {
            addEmailLoginFragment();
            switchLoginTv.setVisibility(View.GONE);
        }

        if (TowerLogin.getInstance().getLoginType().equals(AccountType.Phone)) {
            addPhoneLoginFragment();
            switchLoginTv.setVisibility(View.GONE);
        }

        if (TowerLogin.getInstance().getLoginType().equals(AccountType.ALL)) {
            addPhoneLoginFragment();
            switchLoginTv.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        loginBtn = findViewById(R.id.loginBtn);
        switchLoginTv = findViewById(R.id.changeLoginTypeTv);
        switchLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localType.equals(AccountType.Phone)) {
                    addEmailLoginFragment();
                    switchLoginTv.setText(R.string.log_in_via_mobile_number);
                } else {
                    addPhoneLoginFragment();
                    switchLoginTv.setText(R.string.log_in_via_email);
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckUtils.checkFastClick()) {
                    if (localType == AccountType.Phone) {
                        phoneEmail.login();
                    }
                    if (localType == AccountType.Email) {
                        emailFragment.login();
                    }
                }
            }
        });

        emailFragment = EmailLoginFragment.newInstance();
        phoneEmail = PhoneLoginFragment.newInstance();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.loginContain, phoneEmail)
                .add(R.id.loginContain, emailFragment)
                .commit();


    }

    private void addPhoneLoginFragment() {
        loginBtn.setEnabled(false);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.show(phoneEmail).hide(emailFragment).commit();
        localType = AccountType.Phone;
    }

    private void addEmailLoginFragment() {
        loginBtn.setEnabled(false);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.show(emailFragment).hide(phoneEmail).commit();
        localType = AccountType.Email;
    }

    public void changeBtnState(boolean enable) {
        loginBtn.setEnabled(enable);
        if (!enable)
            finishLoading();
    }

    public void startLoading() {
        loginBtn.startLoading();
    }

    public void finishLoading() {
        loginBtn.endLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isLoginSuccess)
            if (TowerLogin.getInstance().getLoginResult() != null) {
                TowerLogin.getInstance().getLoginResult().onCancel();
            }
    }

    public void loginFinish(JsonObject response, LoginBaseFragment.LoginType loginType) {
        finishLoading();
        isLoginSuccess = true;
        final String authToken = response.get("data").getAsJsonObject().get("authToken").getAsString();
        boolean passwordSet = response.get("data").getAsJsonObject().get("passwordSet").getAsBoolean();
        ToastUtil.getInstance(this).showCustomer(this, R.string.log_in_successful);

        if (passwordSet){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    TowerLogin.getInstance().getLoginResult().onSuccess(authToken);
                    finish();
                }
            }, 500);
        }else {
            Intent intent = new Intent(this, SetPwdActivity.class);
            intent.putExtra("authToken", authToken);
            startActivity(intent);
            finish();
        }
    }
}
