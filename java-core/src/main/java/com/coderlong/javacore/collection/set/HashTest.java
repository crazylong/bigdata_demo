package com.coderlong.javacore.collection.set;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Long Qiong
 * @create 2019/3/30
 */
public class HashTest {


    public static void main(String[] args) {
       HashTest ht = new HashTest();
       A a1 = new A();
       A a2 = new A();
        System.out.println("a1 =" + a1);
        System.out.println("a2=" + a2);
        System.out.println(a1.equals(a2));
       B b1 = new B();
       B b2 = new B();
        System.out.println("b1="+b1);
        System.out.println("b2="+b2);
        System.out.println(b1.equals(b2));

        C c1 = new C();
        C c2 = new C();
        System.out.println("c1="+c1);
        System.out.println("c2="+c2);
        System.out.println(c1.equals(c2));

        Set s = new HashSet();
        s.add(a1);
        s.add(a2);
        s.add(b1);
        s.add(b2);
        s.add(c1);
        s.add(c2);
        System.out.println(s);
    }
}

class A{
    public boolean equals(Object obj){
        return true;
    }
}

class B {
    public int hashCode(){
        return 1;
    }
}

class C {
    public boolean equals(Object obj){
        return true;
    }

    public int hashCode(){
        return 2;
    }
}
