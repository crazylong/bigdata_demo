package com.coderlong.bigdata.java.springboot.spark;

import com.coderlong.bigdata.java.springboot.spark.examples.WordCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Long Qiong
 * @create 2018/11/19
 */
@SpringBootApplication
public class SpringBootSparkApplication implements CommandLineRunner {
    @Autowired
    private WordCount wordCount;

    @Override
    public void run(String... args) throws Exception {
        wordCount.count();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSparkApplication.class, args);
    }
}
