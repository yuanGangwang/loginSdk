package com.guuidea.towersdk.net;

import android.text.TextUtils;

public class Constants {

    private static final Constants INSTANCE = new Constants();
    /**
     * 是否debug包
     */
    private boolean debug = false;
    private String debugUrl;
    private String releaseUrl;
    private String serverPath;

    private Constants() {
    }

    public static Constants getInstance() {
        return INSTANCE;
    }

    public Constants debug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public Constants debugUrl(String url) {
        this.debugUrl = url;
        return this;
    }

    public Constants releaseUrl(String url) {
        this.releaseUrl = url;
        return this;
    }

    public void init() {
        if (TextUtils.isEmpty(debugUrl)) {
            throw new RuntimeException("debugUrl can't be empty!");
        }
        if (TextUtils.isEmpty(releaseUrl)) {
            throw new RuntimeException("releaseUrl can't be empty!");
        }
        serverPath = debug ? debugUrl : releaseUrl;
    }

    //发送手机登录验证码
    public final String getPhoneCodeUrl = serverPath + "/login/sms-verify-code/send";

    //发送重置手机手机密码验证码
    public final String getChangePhonePwdCode = serverPath + "/user/password/reset/sms-verify-code/send";

    //发送邮箱登录验证码
    public final String getEmailCodeUrl = serverPath + "/login/email-verify-code/send";

    //发送重置邮箱登录密码验证码
    public final String getChangeEmailPwdCode = serverPath + "/user/password/reset/email-verify-code/send";

    //获取展示地区
    public final String getSPhoneArea = serverPath + "/common/country/info";

    //手机密码登录
    public final String loginWithPhonePwd = serverPath + "/login/login-by-phone-password";

    //Email密码登录
    public final String loginWithEmailPwd = serverPath + "/login/login-by-email-password";

    //手机验证码登录
    public final String loginWithPhoneCode = serverPath + "/login/login-by-sms-code ";

    //Email验证码登录
    public final String loginWithEmailCode = serverPath + "/login/login-by-email-code";

    //校验修改密码手机验证码
    public String checkChangePhonePwdCode = serverPath + "/user/password/reset/sms-verify-code/check";

    //校验修改密码邮箱验证码
    public String checkChangeEmailPwdCode = serverPath + "/user/password/reset/email-verify-code/check";

    //重置密码
    public String resetPwd = serverPath + "/user/password/reset/confirm";

    //登录状态下设置密码
    public String setPwd = serverPath + "/login/set-password";

}
