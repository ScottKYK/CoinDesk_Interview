package com.scott.coindesk_demo1.Service;


import com.alibaba.fastjson.JSON;
import com.google.gson.*;
import com.scott.coindesk_demo1.Repository.CoinRepository;
import com.scott.coindesk_demo1.pojo.CoinDesk;
import com.scott.coindesk_demo1.pojo.CoinDeskConstant;
import com.scott.coindesk_demo1.util.CoinDeskUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.scott.coindesk_demo1.util.CoinDeskUtils.getNewCoin;
import static com.scott.coindesk_demo1.util.utils.nowTime;


@Service
public class CoinAPIService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CoinRepository coinRepository;
    @Autowired
    private Gson gson;

    // 原始API
    public String getCoindeskAPI() {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    // 客製化API
    public List<CoinDesk> TransferCoindeskAPI() {
        String coinDeskAPI = getCoindeskAPI();
        if (coinDeskAPI.isEmpty()) { //呼叫不到json
            return null;
        }
        //將API轉為JSON格式
        JsonObject jsonObject = gson.fromJson(coinDeskAPI, JsonObject.class);

        List<CoinDesk> list = coinRepository.findAll();

        CoinDesk usdCoin = CoinDeskUtils.coinDeskAPI(jsonObject, CoinDeskConstant.USD);
        CoinDesk gbpCoin = CoinDeskUtils.coinDeskAPI(jsonObject, CoinDeskConstant.GBP);
        CoinDesk eurCoin = CoinDeskUtils.coinDeskAPI(jsonObject, CoinDeskConstant.EUR);

        // 資料庫沒有CoinDesk資料
        if (list.isEmpty()) {
            list.add(usdCoin);
            list.add(gbpCoin);
            list.add(eurCoin);
            System.out.println("isEmpty");
        } else {
            list = new ArrayList<>();
            Optional<CoinDesk> oldUsd = coinRepository.findByCode(CoinDeskConstant.USD);
            Optional<CoinDesk> oldGbp = coinRepository.findByCode(CoinDeskConstant.GBP);
            Optional<CoinDesk> oldEur = coinRepository.findByCode(CoinDeskConstant.EUR);

            list.add(getNewCoin(oldUsd,usdCoin));
            list.add(getNewCoin(oldGbp,gbpCoin));
            list.add(getNewCoin(oldEur,eurCoin));

            System.out.println("noEmpty");
        }
        coinRepository.saveAll(list);
        return list;
    }

    // 新增幣別對應表資料
    public ResponseEntity<String> addCoinDesk(CoinDesk coindesk) {
        if (coindesk.getName() == null) {
            return ResponseEntity.badRequest().body(JSON.toJSONString("請輸入中文名稱"));
        }
        if (coindesk.getRate() == null) {
            return ResponseEntity.badRequest().body("請輸入匯率");
        }
        if (coindesk.getCode() == null) {
            return ResponseEntity.badRequest().body("請輸入幣別");
        }
        if (coinRepository.findByCode(coindesk.getCode()).isPresent()){
            return ResponseEntity.badRequest().body("此幣別已存在");
        }

        CoinDesk newConDesk = new CoinDesk();
        newConDesk.setCode(coindesk.getCode());
        newConDesk.setName(coindesk.getName());
        newConDesk.setRate(coindesk.getRate());
        newConDesk.setUpdateTime(nowTime());

        CoinDesk save = coinRepository.save(newConDesk);
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/json")).build();
    }

    public ResponseEntity<String> updateCoinDesk(CoinDesk coindesk, String code) {
        Optional<CoinDesk> oldCoinDesk = coinRepository.findByCode(code);
        // 不存在的幣別不能更新
        if (!oldCoinDesk.isPresent()) {
            return ResponseEntity.badRequest().body("不存在的幣別不能更新,請先新增");
        }else {
            CoinDesk coinDesk = oldCoinDesk.get();
            coinDesk.setName(coindesk.getName());
            coinDesk.setUpdateTime(nowTime());
            coinDesk.setRate(coindesk.getRate());
            return ResponseEntity.ok().contentType(MediaType.valueOf("application/json")).body(JSON.toJSONString(coinRepository.save(coinDesk)));
        }
    }

    public ResponseEntity<String> deleteByCode(String code) {
        // 不存在的幣別不能刪除
        if (!coinRepository.findByCode(code).isPresent()) {
            return ResponseEntity.badRequest().body("不存在的幣別不能刪除");
        }else {
            coinRepository.delete(coinRepository.findByCode(code).get());
            return ResponseEntity.ok().contentType(MediaType.valueOf("application/json")).build();
        }
    }


}
