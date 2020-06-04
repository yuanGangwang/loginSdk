package com.guuidea.towersdk.net;

public class Constants {

    private final static String serverPath = "http://190.1.1.241:5002";

    //发送手机登录验证码
    public final static String getPhoneCodeUrl = serverPath + "/api/user/login/sms/send";
    //发送重置手机手机密码验证码
    public final static String getChangePhonePwdCode = serverPath + "/api/user/password/sms/send";

    //发送邮箱登录验证码
    public final static String getEmailCodeUrl = serverPath + "/api/user/login/email/send";
    //发送重置邮箱登录密码验证码
    public final static String getChangeEmailPwdCode = serverPath + "/api/user/password/email/send";


    //获取展示地区
    public final static String getSPhoneArea = serverPath + "/api/common/country/info";

    //手机密码登录
    public final static String loginWithPhonePwd = serverPath + "/api/oauth/login";

    //Email密码登录
    public final static String loginWithEmailPwd = serverPath + "/api/oauth/login";

    //手机验证码登录
    public final static String loginWithPhoneCode = serverPath + "/api/oauth/login ";

    //Email验证码登录
    public final static String loginWithEmailCode = serverPath + "/api/oauth/login";

    //校验修改密码手机验证码
    public static String checkChangePhonePwdCode =serverPath+"/api/user/password/sms/check";
    //校验修改密码邮箱验证码
    public static String checkChangeEmailPwdCode =serverPath+"/api/user/password/email/check";

    //重置密码
    public static String resetPwd =serverPath+"/api/user/password/reset";
}
