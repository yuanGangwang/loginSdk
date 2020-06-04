package com.guuidea.towersdk.utils;

import android.text.TextUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class CheckUtils {

    /**
     * @param phone  帶区号的手机(  +86 17342068539)
     * @param region 国家字段( CH )
     * @return
     */
    public static boolean checkPhone(String phone, String region) {

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(region)) {
            return false;
        }

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(phone, region);
            return phoneUtil.isPossibleNumber(swissNumberProto);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 邮箱检查
     */
    public static Boolean checkEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return email.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
    }

    /**
     * 密码检查
     */
    public static boolean checkPwd(String pwd1, String pwd2) {

        if (TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)) {
            return false;
        }
        boolean pwdM1 = pwd1.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
        boolean pwdM2 = pwd2.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
        return pwdM1 && pwdM2 && pwd1.equals(pwd2);
    }

}
