package com.guuidea.towersdk;

public interface LoginResult {
    //登录成功 返回用户鉴权凭证
    void onSuccess(String authToken);

    //登录失败
    void onError(Throwable throwable);

    //取消登录
    void onCancel();
}
