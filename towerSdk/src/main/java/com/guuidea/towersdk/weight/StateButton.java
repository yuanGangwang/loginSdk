package com.guuidea.towersdk.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.guuidea.towersdk.R;

public class StateButton extends FrameLayout {
    private final Context mContext;
    private final View view;
    private TextView title;
    private ProgressBar loadBar;
    private RelativeLayout btnRoot;

    public StateButton(@NonNull Context context) {
        this(context, null);
    }

    public StateButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StateButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.btn_layout, null);

        addView(view);
        initView(attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs, int defStyleAttr) {
        title = ((TextView) findViewById(R.id.text));
        loadBar = ((ProgressBar) findViewById(R.id.loadBar));
        btnRoot = ((RelativeLayout) findViewById(R.id.btnRoot));
        loadBar.setVisibility(GONE);

        if (attrs != null) {
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.StateButton, defStyleAttr, 0);

            title.setText(a.getString(R.styleable.StateButton_btnTitle));

            a.recycle();

        }
    }

    public void startLoading() {
        loadBar.setVisibility(VISIBLE);
    }

    public void endLoading() {
        loadBar.setVisibility(GONE);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        btnRoot.setOnClickListener(l);
    }

    @Override
    public void setEnabled(boolean enabled) {
        btnRoot.setEnabled(enabled);
    }

}
