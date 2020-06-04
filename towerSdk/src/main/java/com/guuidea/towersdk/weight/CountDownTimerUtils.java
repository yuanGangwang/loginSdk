package com.guuidea.towersdk.weight;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.guuidea.towersdk.R;

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;


    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setBackgroundResource(R.drawable.code_send_bg);
        mTextView.setText(millisUntilFinished / 1000 + "s");  //设置倒计时时间
        mTextView.setTextColor(R.color.gray_99); //设置按钮为灰色，这时是不能点击的
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onFinish() {
        mTextView.setText(R.string.Send);
        mTextView.setClickable(true);//重新获得点击
        mTextView.setBackgroundResource(R.drawable.code_unsend_bg);
        mTextView.setTextColor(R.color.login_main_color);  //还原背景色
    }
}
