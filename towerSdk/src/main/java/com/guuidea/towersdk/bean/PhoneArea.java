package com.guuidea.towersdk.bean;

public class PhoneArea {


    boolean showTag;
    private String name;
    private String areaFlag;
    private String code;
    private String tag;

    public PhoneArea() {
    }

    public PhoneArea(String name, String areaFlag, String code) {
        this.name = name;
        this.areaFlag = areaFlag;
        this.code = code;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaFlag() {
        return areaFlag;
    }

    public void setAreaFlag(String areaFlag) {
        this.areaFlag = areaFlag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
