package com.coderlong.javacore.object;

import com.coderlong.javacore.object.interfacedemo.MyInterface;
import com.coderlong.javacore.object.interfacedemo.MyInterfaceImpl;
import org.junit.Test;

/**
 * @author Long Qiong
 * @create 2019/3/25
 */
public class MyTest {
    @Test
    public void testInterface(){
        System.out.println(MyInterface.staticTest());

        MyInterfaceImpl myInterface = new MyInterfaceImpl();
        myInterface.print("hello");
        myInterface.out();
    }
}
