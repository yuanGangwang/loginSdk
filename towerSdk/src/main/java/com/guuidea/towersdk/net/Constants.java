package com.guuidea.towersdk.net;

public class Constants {

//    private final static String serverPath = "http://47.110.12.104:9000";
    private final static String serverPath = "http://api-gateway.globalneutron.com";

    //发送手机登录验证码
    public final static String getPhoneCodeUrl = serverPath + "/login/sms-verify-code/send";

    //发送重置手机手机密码验证码
    public final static String getChangePhonePwdCode = serverPath + "/user/password/reset/sms-verify-code/send";

    //发送邮箱登录验证码
    public final static String getEmailCodeUrl = serverPath + "/login/email-verify-code/send";

    //发送重置邮箱登录密码验证码
    public final static String getChangeEmailPwdCode = serverPath + "/user/password/reset/email-verify-code/send";

    //获取展示地区
    public final static String getSPhoneArea = serverPath + "/common/country/info";

    //手机密码登录
    public final static String loginWithPhonePwd = serverPath + "/login/login-by-phone-password";

    //Email密码登录
    public final static String loginWithEmailPwd = serverPath + "/login/login-by-email-password";

    //手机验证码登录
    public final static String loginWithPhoneCode = serverPath + "/login/login-by-sms-code ";

    //Email验证码登录
    public final static String loginWithEmailCode = serverPath + "/login/login-by-email-code";

    //校验修改密码手机验证码
    public static String checkChangePhonePwdCode = serverPath + "/user/password/reset/sms-verify-code/check";

    //校验修改密码邮箱验证码
    public static String checkChangeEmailPwdCode = serverPath + "/user/password/reset/email-verify-code/check";

    //重置密码
    public static String resetPwd = serverPath + "/user/password/reset/confirm";

    //登录状态下设置密码
    public static String setPwd = serverPath + "/login/set-password";

}
