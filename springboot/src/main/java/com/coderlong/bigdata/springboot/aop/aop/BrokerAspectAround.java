package com.coderlong.bigdata.springboot.aop.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
* @desc: 经纪人切面
* @author: CSH
**/
//@Aspect
@Component
@Log4j2
public class BrokerAspectAround {
 
    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.coderlong.bigdata.springboot.aop.controller.AopController.*(..)))")
    public void BrokerAspect(){
 
    }

    /**
     * @description  使用环绕通知
     */
    @Around("BrokerAspect()")
    public void doAroundGame(ProceedingJoinPoint pjp) throws Throwable {
        try{
            System.out.println("Around:经纪人正在处理球星赛前事务！");
            pjp.proceed();
            System.out.println("Around:返回通知：经纪人为球星表现疯狂鼓掌！");
        }
        catch(Throwable e){
            System.out.println("Around:异常通知：球迷要求退票！");
        }
    }
}