package com.guuidea.towersdk.fragment;

import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.guuidea.towersdk.LoginActivity;
import com.guuidea.towersdk.utils.ToastUtil;

abstract class LoginBaseFragment extends Fragment {

    int selectPosition = 1;

    void  startLogin(){
        if (getActivity() != null) {
            ((LoginActivity) getActivity()).startLoading();
        }
    }

    void loginSuccess(JsonObject jsonObject) {
        if (getActivity() != null) {
            ((LoginActivity) getActivity()).loginFinish(jsonObject);
        }
    }

    void loginError(Throwable throwable) {
        showCommonToast(throwable.getMessage());
        if (getActivity() != null)
            ((LoginActivity) getActivity()).finishLoading();
    }

    void showCommonToast(String content) {
        ToastUtil.getInstance(getContext()).showCommon(content);
    }

    void changeLoginBtnState(String account, String content) {
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(content) && getActivity() != null) {
            ((LoginActivity) getActivity()).changeBtnState(true);
        }else {
            ((LoginActivity) getActivity()).changeBtnState(false);
        }
    }

    public void login() {
        if (selectPosition == 1) {
            loginWithCode();
        }
        if (selectPosition == 2) {
            loginWithPwd();
        }
    }

     abstract void loginWithPwd() ;
     abstract void loginWithCode() ;

}
