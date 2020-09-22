package com.guuidea.towersdk.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.gson.JsonObject;
import com.guuidea.net.CallBackUtil;
import com.guuidea.net.NetClient;
import com.guuidea.towersdk.R;
import com.guuidea.towersdk.weight.LoginCodeView;
import com.guuidea.towersdk.weight.LoginPwdView;

public class PhoneLoginFragment extends LoginBaseFragment {

    private static final String TAG = "TowerMsg";
    EditText codeAccountEt;
    EditText pwdAccountEd;
    private LoginViewModel mViewModel;
    private LoginCodeView codeLayout;
    private LoginPwdView pwdLayout;
    private TextView tabSms;
    private TextView tabPwd;
    private View pwdLine;
    private View smsLine;
    private View tabPwdLayout;
    private View tabSmsLayout;

    public static PhoneLoginFragment newInstance(String appKey) {
        PhoneLoginFragment phoneLoginFragment = new PhoneLoginFragment();
        return phoneLoginFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.phone_login_fragment, container, false);
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
        tabSmsLayout = view.findViewById(R.id.tabSmsLayout);
        tabPwdLayout = view.findViewById(R.id.tabPwdLayout);


        tabSmsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition = 1;
                changeTabChoose();
            }
        });

        tabPwdLayout.setOnClickListener(new View.OnClickListener() {
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
                requestPhoneCode(areaCode, account);
            }

            @Override
            public void getEmailCode(String account) {

            }
        });

        codeAccountEt = codeLayout.getAccountView().getAccountEt();
        pwdAccountEd = pwdLayout.getAccountView().getAccountEt();

        changeTabChoose();
    }

    private void requestPhoneCode(String areaCode, String account) {
        mViewModel.getPhoneCode(areaCode, account, new CallBackUtil() {
            @Override
            public void onFailure(Throwable throwable) {
                showCommonToast(throwable.getMessage());
            }

            @Override
            public void onResponse(JsonObject response) {
                codeLayout.startCountDown();
                showCommonToast(getString(R.string.codeSendTips));
            }
        });
    }


    private void changeTabChoose() {
        tabSms.setSelected(selectPosition == 1);
        tabPwd.setSelected(selectPosition == 2);


        codeLayout.setVisibility(selectPosition == 1 ? View.VISIBLE : View.INVISIBLE);
        pwdLayout.setVisibility(selectPosition == 2 ? View.VISIBLE : View.INVISIBLE);

        if (selectPosition == 1) {
            if (!TextUtils.isEmpty(pwdAccountEd.getText())) {
                codeAccountEt.post(new Runnable() {
                    @Override
                    public void run() {
                        codeAccountEt.setText(pwdAccountEd.getText());
                    }
                });
            }
        }
        if (selectPosition == 2) {
            if (!TextUtils.isEmpty(codeAccountEt.getText())) {
                pwdAccountEd.post(new Runnable() {
                    @Override
                    public void run() {
                        pwdAccountEd.setText(codeAccountEt.getText());
                    }
                });
            }
        }


        Drawable selectDrawable = ContextCompat.getDrawable(getContext(), R.color.login_main_color);
        Drawable unSelectDrawable = ContextCompat.getDrawable(getContext(), R.color.gray_cd);
        smsLine.setBackground(selectPosition == 1 ? selectDrawable : unSelectDrawable);
        pwdLine.setBackground(selectPosition == 2 ? selectDrawable : unSelectDrawable);

    }


    @Override
    void loginWithPwd() {
        startLogin();
        mViewModel.loginWithPhonePwd(pwdLayout.getAccountView().getAreaCode(), pwdLayout.getAccountView().getAccount()
                , pwdLayout.getPwd(), new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        loginError(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        loginSuccess(response, LoginType.pwd);
                    }
                }
        );
    }

    @Override
    void loginWithCode() {
        startLogin();
        mViewModel.loginWithPhoneCode(codeLayout.getAccountView().getAreaCode(), codeLayout.getAccountView().getAccount()
                , codeLayout.getCode(), new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        loginError(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        loginSuccess(response, LoginType.code);
                    }
                }
        );
    }
}
