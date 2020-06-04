package com.guuidea.towersdk.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.guuidea.towersdk.R;

public class ToastUtil {

    private static final String TAG = "ToastUtil";

    private static ToastUtil mToastUtils;
    private static Toast mToast;
    private Context context;

    private ToastUtil(Context context) {
        this.context = context;
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
    }

    public static synchronized ToastUtil getInstance(Context context) {
        if (null == mToastUtils) {
            mToastUtils = new ToastUtil(context);
        }
        return mToastUtils;
    }

    public void showCommon(String content) {
        TextView view = new TextView(context);
        view.setBackgroundResource(R.drawable.toast_common_bg);
        view.setTextSize(12);
        view.setTextColor(Color.WHITE);
        view.setPadding(48, 36, 48, 36);
        view.setText(content);
        mToast.setView(view);
        mToast.show();
    }


    /**
     * 自定义布局toast
     * ToastUtil.getInstance(ChangePwdActivity.this).showCustomer(ChangePwdActivity.this, R.layout.toast);
     *
     * @param context
     * @param
     */
    public void showCustomer(Context context, int stringId) {
        View toastView = LayoutInflater.from(context).inflate(R.layout.toast, null, true);
        ((TextView) toastView.findViewById(R.id.toastText)).setText(context.getString(stringId));
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }


}
