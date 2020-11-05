package com.coderlong.bigdata.springboot.aop.beenorder.order;

import org.springframework.core.annotation.Order;

@Order(2)
public class Good implements Grade{

    {
        System.out.println("2:good");
    }
    @Override
    public int getRating() {
        return 2;
    }
}
