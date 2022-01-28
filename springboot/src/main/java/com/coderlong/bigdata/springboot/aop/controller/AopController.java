package com.coderlong.bigdata.springboot.aop.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aopController")
public class AopController {
    @RequestMapping(value = "/Curry")
    public String Curry(){
        System.out.println("库里上场打球了！！");
        return "库里上场打球了！！";
    }

    @RequestMapping(value = "/Harden")
    public void Harden(){
        System.out.println("哈登上场打球了！！");
    }

    @RequestMapping(value = "/Antetokounmpo")
    public void Antetokounmpo(){
        System.out.println("字母哥上场打球了！！");
    }

    @RequestMapping(value = "/Jokic")
    public void Jokic(){
        System.out.println("约基奇上场打球了！！");
    }

    @RequestMapping(value = "/Durant/{point}")
    public void Durant(@PathVariable("point")  int point){
        System.out.println("杜兰特上场打球了！！");
    }
}
