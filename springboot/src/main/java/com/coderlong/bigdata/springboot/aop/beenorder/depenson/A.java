package com.coderlong.bigdata.springboot.aop.beenorder.depenson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class A {
    @Autowired
    C c;


    @Bean
    public B getB(){
        return new B();
    }
}
