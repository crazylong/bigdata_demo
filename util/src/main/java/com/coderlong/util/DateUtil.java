package com.coderlong.util;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

    @Test
    public void test01(){
        Long dateTime = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        System.out.println(sdf.format(new Date(dateTime)));
    }

    @Test
    public void test02(){
        Calendar calendar = Calendar.getInstance(); // java.util包
        calendar.add(Calendar.DATE, -1); // 如果不够减会将月变动

        Date date = calendar.getTime();
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String dateStr = sdfTime.format(date);

        try {
            calendar.setTime(sdfTime.parse(dateStr));
            System.out.println(calendar.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test03(){
        Map<String, Object> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);

        for(Map.Entry<String, Object> entry : m.entrySet()){
            System.out.println(entry);
        }
    }

}
