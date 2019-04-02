package com.coderlong.javacore.generic.constructor;

/**
 * 泛型构造器
 * @author Long Qiong
 * @create 2019/3/27
 */
public class GenericConstructor {
    public static void main(String[] args) {
        new Foo("hi");
        new Foo(200);
        new <String>Foo("hid");
        //new <String>Foo(1234);
        Foo f1 = new <String>Foo("hid");

        //1.
        MyClass<String> myClass = new MyClass<String>("abd");
        //2.省略String,采用菱形语法
        MyClass<String> myClass1 = new MyClass<>("abd");

        //3.错误，
        //MyClass类声明的类型形参为T，构造器函数声明的形参为E，
        //此时显示声明类型实参为String,构造器实参为Integer，则不能使用菱形语法
        //MyClass<String> myClass2 = new <Integer>MyClass<>(123);

        //4.
        MyClass<String> myClass3 = new <Integer>MyClass<String>(123);


    }
}

class Foo{
    public <T> Foo(T t){
        System.out.println(t);
    }
}

class MyClass<T>{
    public <E> MyClass(E e){
        System.out.println(e);
    }
}