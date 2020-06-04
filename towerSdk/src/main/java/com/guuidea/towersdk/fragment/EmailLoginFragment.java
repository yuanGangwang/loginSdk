package com.guuidea.towersdk.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.gson.JsonObject;
import com.guuidea.towersdk.R;
import com.guuidea.towersdk.net.CallBackUtil;
import com.guuidea.towersdk.weight.LoginCodeView;
import com.guuidea.towersdk.weight.LoginPwdView;


public class EmailLoginFragment extends LoginBaseFragment {

    EditText codeAccountEt;
    EditText pwdAccountEd;
    private LoginViewModel mViewModel;
    private LoginCodeView codeLayout;
    private LoginPwdView pwdLayout;
    private RadioButton tabSms;
    private RadioButton tabPwd;
    private View pwdLine;
    private View smsLine;

    public static EmailLoginFragment newInstance() {
        return new EmailLoginFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_email_login, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new LoginViewModel();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabSms = view.findViewById(R.id.tabSms);
        tabPwd = view.findViewById(R.id.tabPwd);

        smsLine = view.findViewById(R.id.tabSmsLine);
        pwdLine = view.findViewById(R.id.tabPwdLine);


        tabSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition = 1;
                changeTabChoose();
            }
        });

        tabPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition = 2;
                changeTabChoose();
            }
        });
        codeLayout = view.findViewById(R.id.codeLayout);
        pwdLayout = view.findViewById(R.id.pwdLayout);

        codeLayout.setOnTextWatcher(new LoginCodeView.OnTextWatcher() {
            @Override
            public void onTextWatcher(String account, String code) {
                changeLoginBtnState(account, code);

            }
        });

        pwdLayout.setOnTextWatcher(new LoginPwdView.OnTextWatcher() {
            @Override
            public void onTextWatcher(String account, String pwd) {
                changeLoginBtnState(account, pwd);
            }
        });

        codeLayout.setOnCodeGetListener(new LoginCodeView.OnCodeGetListener() {
            @Override
            public void getPhoneCode(String areaCode, String account) {

            }

            @Override
            public void getEmailCode(String account) {
                requestEmailCode(account);
            }
        });

        codeAccountEt = codeLayout.getAccountView().getAccountEt();
        pwdAccountEd = pwdLayout.getAccountView().getAccountEt();

        changeTabChoose();
    }

    private void requestEmailCode(String account) {
        mViewModel.getEmailCode(account, new CallBackUtil() {
            @Override
            public void onFailure(Throwable throwable) {
                showCommonToast(throwable.getMessage());
            }

            @Override
            public void onResponse(JsonObject response) {
                Log.i("TowerMsg", "onResponse: Email验证码已发送");
            }
        });
    }


    @Override
    void loginWithPwd() {
        mViewModel.loginWithEmailPwd(
                pwdLayout.getAccountView().getAccount()
                , pwdLayout.getPwd(), new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        loginError(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        loginSuccess(response);
                    }
                }
        );
    }

    @Override
    void loginWithCode() {
        mViewModel.loginWithEmailCode(codeLayout.getAccountView().getAccount()
                , codeLayout.getCode(), new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        loginError(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        loginSuccess(response);
                    }
                }
        );
    }

    @SuppressLint("ResourceAsColor")
    private void changeTabChoose() {
        tabSms.setChecked(selectPosition == 1);
        tabPwd.setChecked(selectPosition == 2);


        codeLayout.setVisibility(selectPosition == 1 ? View.VISIBLE : View.INVISIBLE);
        pwdLayout.setVisibility(selectPosition == 2 ? View.VISIBLE : View.INVISIBLE);

        if (selectPosition == 1) {
            if (!TextUtils.isEmpty(pwdAccountEd.getText())) {
                codeAccountEt.post(new Runnable() {
                    @Override
                    public void run() {
                        codeLayout.getAccountView().setAccount(pwdAccountEd.getText().toString());
                    }
                });
            }
        }
        if (selectPosition == 2) {
            if (!TextUtils.isEmpty(codeAccountEt.getText())) {
                pwdAccountEd.post(new Runnable() {
                    @Override
                    public void run() {
                        pwdLayout.getAccountView().setAccount(codeAccountEt.getText().toString());
                    }
                });
            }
        }


        Drawable selectDrawable = ContextCompat.getDrawable(getContext(), R.color.login_main_color);
        Drawable unSelectDrawable = ContextCompat.getDrawable(getContext(), R.color.gray_cd);
        smsLine.setBackground(selectPosition == 1 ? selectDrawable : unSelectDrawable);
        pwdLine.setBackground(selectPosition == 2 ? selectDrawable : unSelectDrawable);

    }
}
