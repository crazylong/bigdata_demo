package com.coderlong.javacore.lambda;

import org.junit.Test;

import javax.swing.*;

/**
 * @author Long Qiong
 * @create 2019/3/26
 */
public class MethodRefer {
    @Test
    public void test01(){
        //2.普通用法
        Converter c2 = from -> Integer.valueOf(from);
        System.out.println("普通用法：" + c2.convert("60"));

        //1.引用类方法
        //函数式接口被实现方法的全部参数传给该类方法作为参数
        Converter c1 = Integer::valueOf;
        System.out.println("引用类方法：" + c1.convert("50"));



        //2.普通用法
        Converter c3 = from -> "fkit.org".indexOf(from);
        System.out.println("普通用法：" + c3.convert("or"));

        //4.引用特定对象的实例方法
        //函数式接口被实现方法的全部参数传给该方法作为参数
        Converter c4 = "fkit.org" :: indexOf;
        System.out.println("引用特定对象的实例方法：" + c4.convert("or"));

        //5.普通用法
        MyTest mt1 = (a, b, c) -> a.substring(b, c);
        System.out.println("普通用法：" + mt1.test("fkit.org", 1, 2));

        //6.引用某类对象的实例方法
        //函数式接口中被实现方法的第一个参数作为调用者
        //后面的参数全部传给该方法作为参数
        MyTest mt2 = String:: substring;
        System.out.println("引用某类对象的实例方法：" + mt2.test("fkit.org", 1, 2));

        //7.普通用法
        YourTest yt1 = (String a) -> new JFrame(a);
        JFrame jf1 = yt1.win("我的窗口");
        System.out.println(jf1);


        //8.构造器引用代替Lambda表达式
        //函数式接口中被实现方法的全部参数传给该构造器作为参数
        YourTest yt2 = JFrame::new;
        JFrame jf2 = yt2.win("我的窗口");

        System.out.println("构造器引用：" + jf2);
    }

    public Integer testConvert(Converter c){
       return c.convert("50");
    }
}

@FunctionalInterface
interface Converter{
    Integer convert(String from);
}

@FunctionalInterface
interface MyTest{
    String test(String a, int b, int c);
}

@FunctionalInterface
interface YourTest{
    JFrame win(String title);
}