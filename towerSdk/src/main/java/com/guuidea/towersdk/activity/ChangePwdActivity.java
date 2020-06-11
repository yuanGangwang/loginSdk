package com.guuidea.towersdk.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.guuidea.towersdk.R;
import com.guuidea.towersdk.bean.TokenBean;
import com.guuidea.towersdk.net.CallBackUtil;
import com.guuidea.towersdk.net.Constants;
import com.guuidea.towersdk.net.HeaderManager;
import com.guuidea.towersdk.net.UrlHttpUtil;
import com.guuidea.towersdk.utils.CheckUtils;
import com.guuidea.towersdk.utils.Sha;
import com.guuidea.towersdk.utils.ToastUtil;
import com.guuidea.towersdk.weight.AccountEtView;
import com.guuidea.towersdk.weight.AccountType;
import com.guuidea.towersdk.weight.LoginCodeView;
import com.guuidea.towersdk.weight.NavigaView;
import com.guuidea.towersdk.weight.PwdEtView;
import com.guuidea.towersdk.weight.StateButton;

public class ChangePwdActivity extends BaseActivity {

    final public static String Type = "AccountType";
    final public static String ACCOUNT = "ACCOUNT";
    private static final String TAG = "ChangePwdActivity";
    //重置密码凭证
    String token = "";
    String accountPhone = "";
    String accountEmail = "";
    private View step_one_int;
    private TextView step_one_txt;
    private View step_two_int;
    private TextView step_two_txt;
    private LoginCodeView codeVerView;
    private int step = 1;
    private AccountType accountType = AccountType.Phone;
    private TextView changeTv;
    private StateButton nextBtn;
    private View setPwdView;
    private PwdEtView pwdOne;
    private PwdEtView pwdTwo;
    private String defaultAccount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        int defaultAccountType = getIntent().getIntExtra(Type, 0);
        defaultAccount = getIntent().getStringExtra(ACCOUNT);
        if (defaultAccountType == 0) {
            accountType = AccountType.Phone;
        }
        if (defaultAccountType == 1) {
            accountType = AccountType.Email;
        }
        initView();

    }

    private void initView() {

        ((NavigaView) findViewById(R.id.title)).setOnBackClickListener(new NavigaView.OnApplyNaviListener() {
            @Override
            public void onBackImgClick() {
               destroy();
            }
        });

        step_one_int = findViewById(R.id.step_one_int);
        step_one_txt = findViewById(R.id.step_one_txt);
        step_two_int = findViewById(R.id.step_two_int);
        step_two_txt = findViewById(R.id.step_two_txt);

        codeVerView = findViewById(R.id.codeVerView);
        codeVerView.setOnTextWatcher(new LoginCodeView.OnTextWatcher() {
            @Override
            public void onTextWatcher(String account, String code) {
                if (accountType.equals(AccountType.Email)) {
                    accountEmail = account;
                }
                if (accountType.equals(AccountType.Phone)) {
                    accountPhone = account;
                }
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(code)) {
                    nextBtn.setEnabled(false);
                } else {
                    nextBtn.setEnabled(true);
                }
            }
        });

        changeTv = findViewById(R.id.changeAccountTypeTv);
        setPwdView = findViewById(R.id.setPwdView);
        pwdOne = findViewById(R.id.pwdOne);
        pwdTwo = findViewById(R.id.pwdTwo);

        pwdOne.setWatcher(new PwdEtView.TextWatcher() {
            @Override
            public void watcher(String con) {
                if (!TextUtils.isEmpty(con) && !TextUtils.isEmpty(pwdTwo.getPwd())) {
                    nextBtn.setEnabled(true);
                } else {
                    nextBtn.setEnabled(false);
                }
            }
        });

        pwdTwo.setWatcher(new PwdEtView.TextWatcher() {
            @Override
            public void watcher(String con) {
                if (!TextUtils.isEmpty(con) && !TextUtils.isEmpty(pwdOne.getPwd())) {
                    nextBtn.setEnabled(true);
                } else {
                    nextBtn.setEnabled(false);
                }
            }
        });

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setEnabled(false);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckUtils.checkFastClick()) {
                    dealStep();
                }
            }
        });

        changeTv.setText(accountType.equals(AccountType.Phone) ? R.string.verify_via_email : R.string.verify_via_phone);
        codeVerView.setCodeType(accountType);

        codeVerView.getAccountView().post(new Runnable() {
            @Override
            public void run() {
                codeVerView.getAccountView().setAccount(defaultAccount);
            }
        });

        codeVerView.setOnCodeGetListener(new LoginCodeView.OnCodeGetListener() {
            @Override
            public void getPhoneCode(String areaCode, String account) {
                getPhoneChangePwdCode(areaCode, account);
            }

            @Override
            public void getEmailCode(String account) {
                getEmailChangePwdCode(account);
            }
        });

        changeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountType.equals(AccountType.Phone)) {
                    accountPhone = codeVerView.getAccountView().getAccount();
                    codeVerView.setDefaultAccount(accountEmail);
                    codeVerView.setCodeType(AccountType.Email);
                    changeTv.setText(R.string.verify_via_phone);
                    accountType = AccountType.Email;
                } else {
                    accountEmail = codeVerView.getAccountView().getAccount();
                    codeVerView.setDefaultAccount(accountPhone);
                    codeVerView.setCodeType(AccountType.Phone);
                    changeTv.setText(R.string.verify_via_email);
                    accountType = AccountType.Phone;
                }
                setStep(1);
            }
        });
    }

    private void destroy() {
        if (step == 1) {
            finish();
        } else {
            setStep(1);
        }
    }

    @Override
    public void onBackPressed() {
        destroy();
    }

    private void dealStep() {
        switch (step) {
            case 1:
                AccountEtView accountView = codeVerView.getAccountView();
                if (accountType.equals(AccountType.Phone)) {
                    if (CheckUtils.checkPhone(accountView.getAreaCode() + accountView.getAccount(), accountView.getAreaName())
                            &&
                            !TextUtils.isEmpty(codeVerView.getCode())
                    ) {
                        checkPhoneCode(accountView.getAreaCode(), accountView.getAccount(), codeVerView.getCode());
                    } else {
                        ToastUtil.getInstance(this).showCommon(getString(R.string.invalid_phone));
                    }
                }

                if (accountType.equals(AccountType.Email)) {
                    if (CheckUtils.checkEmail(accountView.getAccount()) &&
                            !TextUtils.isEmpty(codeVerView.getCode())
                    ) {
                        checkEmailCode(accountView.getAccount(), codeVerView.getCode());
                    } else {
                        ToastUtil.getInstance(this).showCommon(getString(R.string.invalid_email));
                    }
                }
                break;
            case 2:

                if (!pwdOne.getPwd().equals(pwdTwo.getPwd())) {
                    showCommonToast(getString(R.string.pwd_not_match));
                    return;
                }
                if (CheckUtils.checkPwd(pwdOne.getPwd(), pwdTwo.getPwd())) {
                    resetPwd(pwdOne.getPwd());
                } else {
                    showCommonToast(getString(R.string.pwd_rules));
                }
                break;
        }
    }

    public void getEmailChangePwdCode(String email) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        UrlHttpUtil.postJson(Constants.getChangeEmailPwdCode, jsonObject.toString()
                , HeaderManager.makeHeader(), new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        showCommonToast(throwable.getMessage());
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        codeVerView.startCountDown();
                        showCommonToast(getString(R.string.codeSendTips));
                    }
                });
    }

    private void getPhoneChangePwdCode(String areaCode, String account) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaCode", areaCode);
        jsonObject.addProperty("phone", account);
        UrlHttpUtil.postJson(Constants.getChangePhonePwdCode, jsonObject.toString()
                , HeaderManager.makeHeader(), new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        showCommonToast(throwable.getMessage());
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        codeVerView.startCountDown();
                        showCommonToast(getString(R.string.codeSendTips));
                    }
                });
    }

    private void checkPhoneCode(String areaCode, String phone, String code) {
        nextBtn.startLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaCode", areaCode);
        jsonObject.addProperty("phone", phone);
        jsonObject.addProperty("code", code);
        UrlHttpUtil.postJson(Constants.checkChangePhonePwdCode, jsonObject.toString()
                , HeaderManager.makeHeader(), new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        nextBtn.endLoading();
                        showCommonToast(throwable.getMessage());
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        verifySuccess(response);
                    }
                });
    }

    private void checkEmailCode(String email, String code) {
        nextBtn.startLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("code", code);
        UrlHttpUtil.postJson(Constants.checkChangeEmailPwdCode, jsonObject.toString()
                , HeaderManager.makeHeader(), new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        nextBtn.endLoading();
                        showCommonToast(throwable.getMessage());
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        verifySuccess(response);
                    }
                });
    }

    private void verifySuccess(JsonObject response) {
        nextBtn.endLoading();
        token = new Gson().fromJson(response.get("data"), TokenBean.class).getToken();
        ToastUtil.getInstance(ChangePwdActivity.this).showCustomer(ChangePwdActivity.this, R.string.pwdCodeVerifySuccess);
        step = 2;
        setStep(2);
    }

    private void resetPwd(String pwd) {
        nextBtn.startLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", token);
        jsonObject.addProperty("password", Sha.getSHA256(pwd));
        UrlHttpUtil.postJson(Constants.resetPwd, jsonObject.toString()
                , HeaderManager.makeHeader(), new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        showCommonToast(throwable.getMessage());
                        nextBtn.endLoading();
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        nextBtn.endLoading();
                        ToastUtil.getInstance(ChangePwdActivity.this).showCustomer(ChangePwdActivity.this, R.string.reset_pwd_success);
                        finishDelay();
                    }
                });
    }

    private void finishDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    private void setStep(int i) {
        step = i;
        codeVerView.setVisibility(step == 1 ? View.VISIBLE : View.GONE);
        setPwdView.setVisibility(step == 1 ? View.GONE : View.VISIBLE);

        changeTv.setVisibility(step == 1 ? View.VISIBLE : View.GONE);

        codeVerView.clearText(accountType.equals(AccountType.Email) ? accountEmail : accountPhone);
        pwdOne.clearText();
        pwdTwo.clearText();


        step_one_int.setBackgroundResource(step == 1 ? R.drawable.step_select : R.drawable.step_unselect);
        step_two_int.setBackgroundResource(step == 2 ? R.drawable.step_select : R.drawable.step_unselect);

        step_one_txt.setTextColor(step == 1 ? ContextCompat.getColor(this, R.color.login_main_color) : ContextCompat.getColor(this, R.color.gray_99));
        step_two_txt.setTextColor(step == 2 ? ContextCompat.getColor(this, R.color.login_main_color) : ContextCompat.getColor(this, R.color.gray_99));

        nextBtn.setText(step == 1 ? getString(R.string.next) : getString(R.string.confirm));
    }

}
