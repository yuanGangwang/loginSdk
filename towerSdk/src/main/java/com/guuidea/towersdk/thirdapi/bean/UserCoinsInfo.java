package com.guuidea.towersdk.thirdapi.bean;

import java.util.Date;
import java.util.List;

public class UserCoinsInfo {
    Integer currentCoins;//当前积分(Integer);

    Integer todayCoins;//今日获得积分(Integer);

    Float redeemableMoney;//可兑换金额(Float);

    int totalCoins;//历史获得总积分(Integer);

    List<CoinHistory> coinsHistory;//获得积分历史记录(List<GetCoinsHistoryDto>;//


    private class CoinHistory {

        Date date;//日期(Date);

        String eventName;//事件名称(String);

        int coins;//所获得积分(Integer)；

    }
}
