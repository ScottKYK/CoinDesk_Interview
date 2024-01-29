package com.scott.coindesk_demo1.Controller;

import com.alibaba.fastjson.JSON;
import com.scott.coindesk_demo1.Repository.CoinRepository;
import com.scott.coindesk_demo1.Service.CoinAPIService;
import com.scott.coindesk_demo1.pojo.CoinDesk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class CoinController {
    @Autowired
    private CoinAPIService coinAPIService;
    @Autowired
    private CoinRepository coinRepository;


    // 呼叫 coindesk 的 API
    @GetMapping("callCoinDesk")
    public ResponseEntity<String> getCoin() {
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/json")).body(coinAPIService.getCoindeskAPI());
    }

    // 呼叫 coindesk 的 API,並進行資料轉換,組成新 API
    @GetMapping("newCoinDesk")
    public ResponseEntity<String> newAPI() {
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/json")).body(JSON.toJSONString(coinAPIService.TransferCoindeskAPI()));
    }

    @GetMapping("findAll")
    public ResponseEntity<String> findAll() {
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/json")).body(JSON.toJSONString(coinRepository.findAll()));
    }

    // 查詢幣別對應表資料
    @GetMapping("find/{code}")
    public ResponseEntity<String> findByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/json")).body(JSON.toJSONString(coinRepository.findByCode(code)));
    }

    //新增幣別對應表資料
    @PostMapping("add")
    public ResponseEntity<String> add(@RequestBody CoinDesk coindesk) {
        return coinAPIService.addCoinDesk(coindesk);
    }

    // 更新幣別對應表資料 , 顯示其內容。
    @PostMapping("update/{code}")
    public ResponseEntity<String> update(@RequestBody CoinDesk coindesk, @PathVariable("code") String code) {
        return coinAPIService.updateCoinDesk(coindesk,code);
    }

    // 刪除幣別對應表資料
    @DeleteMapping("delete/{code}")
    public ResponseEntity<String> delete(@PathVariable("code") String code) {
        return coinAPIService.deleteByCode(code);
    }


}
