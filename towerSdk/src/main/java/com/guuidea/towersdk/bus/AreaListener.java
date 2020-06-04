package com.guuidea.towersdk.bus;

import com.guuidea.towersdk.bean.PhoneArea;

import java.util.ArrayList;
import java.util.List;

public class AreaListener {
    private static volatile AreaListener areaListener = null;
    private PhoneArea phoneArea = new PhoneArea("United States of Americ", "US", "1");
    private List<AreaChooseListener> areaChooseListeners = new ArrayList<>();

    public static AreaListener getInstance() {
        if (areaListener == null) {
            synchronized (AreaListener.class) {
                if (areaListener == null) {
                    areaListener = new AreaListener();
                }
            }
        }
        return areaListener;
    }

    public void addChooseListener(AreaChooseListener areaChooseListener) {
        if (!areaChooseListeners.contains(areaChooseListener)) {
            areaChooseListeners.add(areaChooseListener);
            areaChooseListener.onChoose(phoneArea);
        }
    }

    public void sendDataToListener() {
        for (AreaChooseListener areaChooseListener : areaChooseListeners) {
            areaChooseListener.onChoose(phoneArea);
        }
    }

    public void updateData(PhoneArea phoneArea) {
        this.phoneArea = phoneArea;
        sendDataToListener();
    }

    public interface AreaChooseListener {
        void onChoose(PhoneArea phoneArea);
    }
}
