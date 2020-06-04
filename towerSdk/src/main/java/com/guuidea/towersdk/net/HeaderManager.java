package com.guuidea.towersdk.net;

import com.guuidea.towersdk.TowerLogin;

import java.util.HashMap;
import java.util.Map;

public class HeaderManager {
    public static Map<String, String> makeHeader() {
        HashMap<String, String> map = new HashMap<>();
        map.put("appkey", TowerLogin.getInstance().getAppKey());
        return map;
    }
}
