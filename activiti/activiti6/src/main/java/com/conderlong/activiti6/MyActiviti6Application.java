package com.conderlong.activiti6;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LongQiong
 * @date 2021/3/2
 */
@SpringBootApplication( exclude = SecurityAutoConfiguration.class)
public class MyActiviti6Application {
    public static void main(String[] args) {
        SpringApplication.run(MyActiviti6Application.class, args);
    }
}
