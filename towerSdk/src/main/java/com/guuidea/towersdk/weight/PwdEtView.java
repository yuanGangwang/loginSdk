package com.guuidea.towersdk.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.guuidea.towersdk.R;

public class PwdEtView extends FrameLayout {
    TextWatcher watcher;
    private Context mContext;
    private View view;
    private EditText pwdEt;
    private View pwdEtLine;
    private String hint;

    public PwdEtView(@NonNull Context context) {
        this(context, null);
    }

    public PwdEtView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PwdEtView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        addView();
        iniView(attrs, defStyleAttr);
    }

    private void addView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.et_pwd, null);
        pwdEt = view.findViewById(R.id.pwdEt);
        pwdEtLine = view.findViewById(R.id.pwdEtLine);
        addView(view);
    }

    private void iniView(AttributeSet attrs, int defStyleAttr) {

        if (attrs != null) {
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.PwdEtView, defStyleAttr, 0);
            hint = a.getString(R.styleable.PwdEtView_pwdHint);
            a.recycle();
        }

        if (!TextUtils.isEmpty(hint)) {
            pwdEt.setHint(hint);
        }

        pwdEt.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (watcher != null) {
                    watcher.watcher(s.toString());
                }
            }
        });

        pwdEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pwdEtLine.setBackground(hasFocus ?
                        ContextCompat.getDrawable(mContext, R.color.login_main_color) :
                        ContextCompat.getDrawable(mContext, R.color.gray_cd)
                );
            }
        });

    }

    public String getPwd() {
        if (pwdEt != null) {
            return (pwdEt.getText()).toString();
        }
        return "";
    }

    public void setWatcher(TextWatcher watcher) {
        this.watcher = watcher;
    }

    public void clearText() {
        pwdEt.setText("");
    }

    public interface TextWatcher {
        void watcher(String con);
    }
}
