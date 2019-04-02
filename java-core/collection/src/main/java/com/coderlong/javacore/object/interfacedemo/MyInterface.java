package com.coderlong.javacore.object.interfacedemo;

/**
 * @author Long Qiong
 * @create 2019/3/25
 */
public interface MyInterface {
    //接口里定义的成员变量只能是常量
    int MAX_CACHE_LINE = 50;
    //接口里定义的普通方法只能是public的抽象方法
    void out();

    //在接口中定义默认方法，需要使用default修饰
    default void print(String... msgs){
        for (String msg : msgs) {
            System.out.println(msg);
        }
    }

    //在接口中定义类方法，需要使用static修饰
    static String staticTest(){
        return "接口里的类方法";
    }
}
