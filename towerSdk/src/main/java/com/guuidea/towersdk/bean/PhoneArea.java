package com.guuidea.towersdk.bean;

public class PhoneArea {


    boolean showTag;
    private String areaName;
    private String areaFlag;
    private String areaCode;
    private String tag;

    public PhoneArea() {
    }

    public PhoneArea(String areaName, String areaFlag, String areaCode) {
        this.areaName = areaName;
        this.areaFlag = areaFlag;
        this.areaCode = areaCode;
    }

    public boolean isShowTag() {
        return showTag;
    }

    public void setShowTag(boolean showTag) {
        this.showTag = showTag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaFlag() {
        return areaFlag;
    }

    public void setAreaFlag(String areaFlag) {
        this.areaFlag = areaFlag;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
