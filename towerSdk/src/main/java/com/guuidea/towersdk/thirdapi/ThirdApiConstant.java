package com.guuidea.towersdk.thirdapi;

public class ThirdApiConstant {

    public static String getUserInfo ;
    public static String updateUserInfo;
    public static String getUserCoin;
    public static String inputInviteCOde;
    private static ThirdApiConstant apiConstant;
    public String thirdServerPath = "";

    public ThirdApiConstant(String thirdServerPath) {
        this.thirdServerPath = thirdServerPath;
        updateUserInfo = thirdServerPath + "/app/user/info/update";
        getUserInfo = thirdServerPath + "/app/user/info/get";
        getUserCoin = thirdServerPath + "/app/user/coins/info/get";
        inputInviteCOde = thirdServerPath + "/app/invitation/code/write";
    }

    public static ThirdApiConstant getInstance(String thirdServerPath) {
        if (apiConstant == null) {
            apiConstant = new ThirdApiConstant(thirdServerPath);
        }
        return apiConstant;
    }

}
