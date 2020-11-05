package com.coderlong.bigdata.springboot.aop.beenorder.order;

import org.springframework.core.annotation.Order;

@Order(1)
public class Excellent implements Grade {
    {
        System.out.println("1:excellent");
    }
    @Override
    public int getRating() {
        return 1;
    }
}
