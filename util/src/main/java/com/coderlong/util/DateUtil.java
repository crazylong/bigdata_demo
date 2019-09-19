package com.coderlong.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    @Test
    public void test01(){
        Long dateTime = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        System.out.println(sdf.format(new Date(dateTime)));
    }

}
