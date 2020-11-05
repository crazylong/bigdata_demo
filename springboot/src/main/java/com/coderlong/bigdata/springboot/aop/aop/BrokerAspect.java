package com.coderlong.bigdata.springboot.aop.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
* @desc: 经纪人切面
* @author: CSH
**/
//@Aspect
@Component
@Log4j2
public class BrokerAspect {
 
    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.coderlong.bigdata.springboot.aop.controller.AopController.*(..)))")
    public void BrokerAspect(){
 
    }
 
    /**
    * @description  在连接点执行之前执行的通知
    */
    @Order(3)
    @Before("BrokerAspect()")
    public void doBeforeGame(){
        System.out.println("经纪人正在处理球星赛前事务！");


    }

    /**
     * @description  在连接点执行之前执行的通知
     */
    @Order(4)
    @Before("BrokerAspect()")
    public void doBeforeGame2(JoinPoint joinPoint){
        System.out.println("经纪人正在处理球星赛前事务2！");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //日志打印：url
        log.info("url={}", request.getRequestURL()); //StringBuffer

        //日志打印：method
        log.info("method={}", request.getMethod());

        //日志打印：ip
        log.info("ip={}", request.getRemoteAddr());

        //日志打印：类方法
        log.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        //日志打印：参数
        log.info("args={}", joinPoint.getArgs());
    }
 
    /**
     * @description  在连接点执行之后执行的通知（返回通知和异常通知的异常）
     */
    @After("BrokerAspect()")
    public void doAfterGame(){
        System.out.println("经纪人为球星表现疯狂鼓掌！");
    }

    /**
     * @description  在连接点执行之后执行的通知（返回通知）
     */
    @AfterReturning("BrokerAspect()")
    public void doAfterReturningGame(){
        System.out.println("返回通知：经纪人为球星表现疯狂鼓掌！");
    }
 
    /**
     * @description  在连接点执行之后执行的通知（异常通知）
     */
    @AfterThrowing("BrokerAspect()")
    public void doAfterThrowingGame(){
        System.out.println("异常通知：球迷要求退票！");
    }
}