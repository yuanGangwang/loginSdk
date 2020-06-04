package com.guuidea.towersdk.weight;

import android.content.Context;
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
import com.guuidea.towersdk.utils.CheckUtils;
import com.guuidea.towersdk.utils.ToastUtil;

public class LoginCodeView extends FrameLayout {
    private Context mContext;
    private View view;

    private int codeType;
    private AccountEtView accountView;
    private TextView tipsWithCodeLogin;
    private TextView getCodeBtn;
    private EditText codeEt;
    private View codeEtLine;
    private OnTextWatcher watcher;
    private OnCodeGetListener codeGetListener;

    public LoginCodeView(@NonNull Context context) {
        this(context, null);
    }

    public LoginCodeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoginCodeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        iniView();
        getAttrs(attrs, defStyleAttr);
    }

    private void getAttrs(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.LoginCodeView, defStyleAttr, 0);
            codeType = a.getInt(R.styleable.LoginCodeView_codeType, 0);
            boolean isLogin = a.getBoolean(R.styleable.LoginCodeView_isLogin, true);

            tipsWithCodeLogin.setVisibility(isLogin ? VISIBLE : GONE);

            if (codeType == 0) {
                tipsWithCodeLogin.setText(R.string.tipLoginPhone);
                accountView.setAccountType(AccountType.Phone);
            } else {
                tipsWithCodeLogin.setText(R.string.tipLoginEmail);
                accountView.setAccountType(AccountType.Email);
            }

            a.recycle();
        }
    }

    public void setCodeType(AccountType accountType) {
        if (accountView != null) {
            accountView.setAccountType(accountType);
        }

        codeType = accountType.ordinal();
    }

    private void iniView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.et_code, null);
        accountView = ((AccountEtView) view.findViewById(R.id.accountView));

        codeEt = view.findViewById(R.id.codeEt);
        codeEtLine = view.findViewById(R.id.codeEtLine);

        tipsWithCodeLogin = ((TextView) view.findViewById(R.id.tipsWithCodeLogin));

        getCodeBtn = ((TextView) view.findViewById(R.id.getCodeBtn));

        getCodeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeType == 0) {
                    if (CheckUtils.checkPhone(accountView.getAreaCode() + accountView.getAccount(), accountView.getAreaName())) {
                        requestPhoneCode();
                        if (codeGetListener != null) {
                            codeGetListener.getPhoneCode(accountView.getAreaCode(), accountView.getAccount());
                        }
                    } else {
                        ToastUtil.getInstance(mContext).showCommon(getContext().getString(R.string.invalid_phone));
                    }
                }
                if (codeType == 1) {
                    if (CheckUtils.checkEmail(accountView.getAccount())) {
                        requestEmailCode();
                        if (codeGetListener != null) {
                            codeGetListener.getEmailCode(accountView.getAccount());
                        }
                    } else {
                        ToastUtil.getInstance(mContext).showCommon(getContext().getString(R.string.invalid_email));
                    }
                }
            }
        });

        codeEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                codeEtLine.setBackground(hasFocus ?
                        ContextCompat.getDrawable(mContext, R.color.login_main_color) :
                        ContextCompat.getDrawable(mContext, R.color.gray_cd)
                );
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
                    watcher.onTextWatcher(accountView.getAccountEt().getText().toString(), codeEt.getText().toString());
                }
            }
        });
        codeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (watcher != null) {
                    watcher.onTextWatcher(accountView.getAccountEt().getText().toString(), codeEt.getText().toString());
                }
            }
        });

        addView(view);
    }

    public AccountEtView getAccountView() {
        return accountView;
    }

    public String getCode() {
        return codeEt.getText().toString();
    }

    private void requestPhoneCode() {
        startCountDown();
    }

    private void requestEmailCode() {
        startCountDown();

    }

    private void startCountDown() {
        CountDownTimerUtils downTimerUtils = new CountDownTimerUtils(getCodeBtn, 60000, 1000);
        downTimerUtils.start();
    }

    public void clearText() {
        codeEt.setText("");
        accountView.clearText();
    }

    public void setOnTextWatcher(OnTextWatcher watcher) {
        this.watcher = watcher;
    }

    public void setOnCodeGetListener(OnCodeGetListener onCodeGetListener) {
        this.codeGetListener = onCodeGetListener;
    }

    public interface OnCodeGetListener {
        void getPhoneCode(String areaCode, String account);

        void getEmailCode(String account);
    }

    public interface OnTextWatcher {
        void onTextWatcher(String account, String code);
    }
}
