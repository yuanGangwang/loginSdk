package com.guuidea.towersdk.weight;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.guuidea.towersdk.R;
import com.guuidea.towersdk.activity.AreaSearchActivity;
import com.guuidea.towersdk.bean.PhoneArea;
import com.guuidea.towersdk.bus.AreaListener;
import com.guuidea.towersdk.utils.HideKeyBroadUtils;

public class AccountEtView extends FrameLayout {
    boolean auto = false;
    private Context mContext;
    private View view;
    private View accountDivider;
    private TextView areaTv;
    private EditText accountEt;
    private AccountType accountType = AccountType.Phone;
    private View accountEtLine;
    private PhoneArea mPhoneArea;
    private EmailSuffixPopUtils popWindows;
    private TextWatcher watcher;

    public AccountEtView(@NonNull Context context) {
        this(context, null);
    }

    public AccountEtView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AccountEtView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        addView();
        iniView();
    }

    private void addView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.et_phone_choice, null);
        areaTv = view.findViewById(R.id.areaTv);
        accountEt = view.findViewById(R.id.accountEt);
        accountDivider = view.findViewById(R.id.accountDivider);
        accountEtLine = view.findViewById(R.id.accountEtLine);
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (accountType == AccountType.Email && !auto) {
                    popWindows = EmailSuffixPopUtils.getInstance(mContext);
                    if (TextUtils.isEmpty(s)) {
                        popWindows.dismiss();
                    } else {
                        if (popWindows.isWindowShowing()) {
                            popWindows.updateEmail(s.toString());
                        } else {
                            popWindows.showPopWindow(accountEt);
                            popWindows.updateEmail(s.toString());
                            popWindows.setOnSuffixClick(new EmailSuffixPopUtils.SuffixAdapter.OnSuffixClick() {
                                @Override
                                public void onClick(String emailAll) {
                                    accountEt.setText(emailAll);
                                    accountEt.setSelection(emailAll.length());
                                    popWindows.dismiss();
                                    HideKeyBroadUtils.HideSoftInput(mContext,view.getWindowToken());
                                }
                            });
                        }
                    }
                }
            }
        };
        accountEt.addTextChangedListener(watcher);
        addView(view);
    }

    private void iniView() {

        areaTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AreaSearchActivity.class);
                mContext.startActivity(intent);
            }
        });


        accountEt.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int action = event.getAction();
                Log.i("onKey", "onKey: "+keyCode);
                if (popWindows!=null&&keyCode==67) {
//                    popWindows.dismiss();
//                    HideKeyBroadUtils.HideSoftInput(mContext,view.getWindowToken());
                }
                return false;
            }
        });

        accountEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus&&popWindows!=null) {
                    popWindows.dismiss();
                    HideKeyBroadUtils.HideSoftInput(mContext,view.getWindowToken());
                }
                accountEtLine.setBackground(hasFocus ?
                        ContextCompat.getDrawable(mContext, R.color.login_main_color) :
                        ContextCompat.getDrawable(mContext, R.color.gray_cd)
                );
            }
        });
        accountEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(accountType.equals(AccountType.Email) ? 64 : 16)});
        areaTv.setVisibility(accountType.equals(AccountType.Email) ? GONE : VISIBLE);
        accountDivider.setVisibility(accountType.equals(AccountType.Email) ? GONE : VISIBLE);
        accountEt.setInputType(accountType.equals(AccountType.Email) ? InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS : InputType.TYPE_CLASS_PHONE);
        accountEt.setHint(accountType.equals(AccountType.Email) ? R.string.email : R.string.mobile_number);
        Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_mail);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            accountEt.setCompoundDrawables(accountType.equals(AccountType.Email) ?
                    drawable : null, null, null, null);
        }

        AreaListener.getInstance().addChooseListener(new AreaListener.AreaChooseListener() {
            @Override
            public void onChoose(PhoneArea phoneArea) {
                mPhoneArea = phoneArea;
                areaTv.setText(String.format("+%s", phoneArea.getAreaCode()));
            }
        });

    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
        iniView();
    }

    public EditText getAccountEt() {
        return accountEt;
    }

    public String getAccount() {
        if (accountEt != null) {
            return (accountEt.getText()).toString();
        }
        return "";
    }

    public void setAccount(final String account) {
        if (accountEt != null) {
            auto = true;
            accountEt.setText(account);
            auto = false;
        }

    }

    public String getAreaCode() {
        if (areaTv != null)
            return mPhoneArea.getAreaCode();

        return "";
    }

    public String getAreaName() {
        if (areaTv != null)
            return mPhoneArea.getAreaFlag();
        return "";
    }

    public void clearText() {
        accountEt.setText("");
    }

}
