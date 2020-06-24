package com.guuidea.towersdk.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.guuidea.towersdk.utils.HideKeyBroadUtils;
import com.guuidea.towersdk.utils.ToastUtil;
import com.guuidea.towersdk.weight.NavigaView;

public class BaseActivity extends AppCompatActivity implements NavigaView.OnApplyNaviListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁用横屏
        super.onCreate(savedInstanceState);
        setTranslucentBar(getWindow());
    }

    /**
     * 沉浸状态栏
     *
     * @param window
     */
    public void setTranslucentBar(Window window) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();// 隐藏ActionBar
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    protected void showCommonToast(String content) {
        ToastUtil.getInstance(this).showCommon(content);
    }

    /**
     * 空白隐藏软键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        HideKeyBroadUtils.hide(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    private static final String TAG = "BaseActivity";
    /**
     * 返回
     */
    @Override
    public void onBackImgClick() {
        finish();
    }
}
