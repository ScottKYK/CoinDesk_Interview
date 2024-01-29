package com.scott.coindesk_demo1;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.scott.coindesk_demo1.util.utils.nowTime;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CoindeskDemo1ApplicationTests {

    @Autowired
    public MockMvc mockMvc;


    @Test
    void callCoinDesk() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/callCoinDesk")
                .headers(httpHeaders);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void newCoinDesk() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/newCoinDesk")
                .headers(httpHeaders);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void findByCode() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/find/USD")
                .headers(httpHeaders);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void add() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        JSONObject request = new JSONObject();
        request.put("code", "TEST");
        request.put("name", "測試貨幣");
        request.put("rate", "12345.67");
        request.put("updateTime", nowTime());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/add")
                .headers(httpHeaders)
                .content(request.toString());
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void updateByCode() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        // 修改相關資訊
        String code = "TEST";
        String name = "測試貨幣2";
        String rate = "65432.1";

        JSONObject request = new JSONObject();
        request.put("code", code);
        request.put("name", name);
        request.put("rate", rate);
        request.put("updateTime", nowTime());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/update/" + code)
                .headers(httpHeaders)
                .content(request.toString());
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void delete() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // 刪除的幣別
        String code = "TEST";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/delete/" + code)
                .headers(httpHeaders);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void findAll() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/findAll")
                .headers(httpHeaders);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }


}
