package com.guuidea.towersdk.weight;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.guuidea.towersdk.R;
import com.guuidea.towersdk.activity.ChangePwdActivity;

public class LoginPwdView extends FrameLayout {
    private Context mContext;
    private View view;
    private AccountEtView accountView;
    private TextView forgetTv;
    private EditText pwdEt;
    private int pwdType;
    private View pwdLine;
    private OnTextWatcher watcher;

    public LoginPwdView(@NonNull Context context) {
        this(context, null);
    }

    public LoginPwdView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoginPwdView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        iniView();
        getAttrs(attrs, defStyleAttr);
    }


    private void getAttrs(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.LoginPwdView, defStyleAttr, 0);
            pwdType = a.getInt(R.styleable.LoginPwdView_pwdType, 0);
            if (pwdType == 0) {
                accountView.setAccountType(AccountType.Phone);
            } else {
                accountView.setAccountType(AccountType.Email);
            }

            a.recycle();
        }
    }

    private void iniView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.login_pwd, null);
        accountView = view.findViewById(R.id.accountView);
        pwdEt = view.findViewById(R.id.pwdEt);
        forgetTv = view.findViewById(R.id.forgetTv);
        pwdLine = view.findViewById(R.id.pwdLine);

        pwdEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pwdLine.setBackground(hasFocus ?
                        ContextCompat.getDrawable(mContext, R.color.login_main_color) :
                        ContextCompat.getDrawable(mContext, R.color.gray_cd)
                );
            }
        });

        forgetTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChangePwdActivity.class);
                intent.putExtra(ChangePwdActivity.Type,pwdType);
                intent.putExtra(ChangePwdActivity.ACCOUNT,accountView.getAccount());
                mContext.startActivity(intent);
            }
        });


        accountView.getAccountEt().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (watcher != null) {
                    watcher.onTextWatcher(accountView.getAccount(), pwdEt.getText().toString());
                }
            }
        });

        pwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (watcher != null) {
                    watcher.onTextWatcher(accountView.getAccount(), pwdEt.getText().toString());
                }
            }
        });

        addView(view);
    }

    public AccountEtView getAccountView(){
        return accountView;
    }

    private String getAccount() {
        return accountView.getAccount();
    }

    public String getPwd() {
        return pwdEt.getText().toString();
    }

    public void setOnTextWatcher(OnTextWatcher watcher) {
        this.watcher = watcher;
    }


    public interface OnTextWatcher {
        void onTextWatcher(String account, String pwd);
    }
}
