package com.coderlong.bigdata.sessionproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Long Qiong
 * @create 2019/5/9
 */
@ComponentScan({"com.jumore.dove.dao","com.jumore.dove.core", "com.coderlong.bigdata.sessionproject"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
