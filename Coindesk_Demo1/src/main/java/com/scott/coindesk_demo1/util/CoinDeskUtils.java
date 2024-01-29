package com.scott.coindesk_demo1.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.scott.coindesk_demo1.pojo.CoinDesk;
import com.scott.coindesk_demo1.pojo.CoinDeskConstant;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.*;

public class CoinDeskUtils {

    // 封裝新的coinDeskAPI
    public static CoinDesk coinDeskAPI(JsonObject jsonObject, String code) {
        CoinDesk coinDesk = new CoinDesk();
        // 獲取API中的時間
        String time = jsonObject.getAsJsonObject(CoinDeskConstant.TIME).getAsJsonPrimitive(CoinDeskConstant.UPDATED_ISO).getAsString();
        // 時間格式化
        coinDesk.setUpdateTime(timeTransfer(time));
        // 幣別
        coinDesk.setCode(code);
        // 匯率
        String rate = jsonObject.getAsJsonObject(CoinDeskConstant.BPI).getAsJsonObject(code).getAsJsonPrimitive(CoinDeskConstant.RATE_FLOAT).getAsString();
        coinDesk.setRate(new BigDecimal(rate));

        String name = "";
        switch (code) {
            case CoinDeskConstant.USD:
                name = CoinDeskConstant.USD_TW;
                break;
            case CoinDeskConstant.GBP:
                name = CoinDeskConstant.GBP_TW;
                break;
            case CoinDeskConstant.EUR:
                name = CoinDeskConstant.EUR_TW;
                break;
        }
        coinDesk.setName(name);

        return coinDesk;
    }

    //時間格式轉換(ISO8601格式=>yyyy/MM/dd HH:mm:ss)
    private static String timeTransfer(String time) {
        DateTimeFormatter dtf1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'+00:00'");
        DateTime dt = dtf1.parseDateTime(time);
        DateTimeFormatter dtf2 = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
        return dt.toString(dtf2);
    }

    public static CoinDesk getNewCoin(Optional<CoinDesk> oldCoin, CoinDesk newCoin) {
        if(oldCoin.isPresent()){
            CoinDesk oldCoinDesk = oldCoin.get();
            oldCoinDesk.setRate(newCoin.getRate());
            oldCoinDesk.setUpdateTime(newCoin.getUpdateTime());
            return oldCoinDesk;
        }else {
            return newCoin;
        }
    }
}
