package com.scott.coindesk_demo1.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class utils {

    public static String nowTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String newDate = sdf.format(date);  // 格式转换
        return newDate;
    }
}
