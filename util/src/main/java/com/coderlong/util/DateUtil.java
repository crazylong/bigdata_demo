package com.coderlong.util;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

}
