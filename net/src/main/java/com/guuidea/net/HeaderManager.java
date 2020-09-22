package com.guuidea.net;

import java.util.HashMap;
import java.util.Map;

public class HeaderManager {
    public static Map<String, String> makeHeader() {
        HashMap<String, String> map = new HashMap<>();
//        map.put("accept-language", LanguageUtils.getInstance().getSysLanguage());
        map.put("accept-language", "en");
        return map;
    }
}
