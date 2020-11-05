package com.coderlong.bigdata.springboot.aop.beenorder.depenson;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class B {
    @Bean
    public C c(){
        return new C();
    }
}
