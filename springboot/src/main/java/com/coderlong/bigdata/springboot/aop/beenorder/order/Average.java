package com.coderlong.bigdata.springboot.aop.beenorder.order;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE)
public class Average implements Grade{
    {
        System.out.println("3.Average");
    }

    @Override
    public int getRating() {
        return 3;
    }
}
