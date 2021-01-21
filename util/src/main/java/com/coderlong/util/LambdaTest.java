package com.coderlong.util;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LambdaTest {
    @Test
    public void test1(){
        List<Integer> l1 = new ArrayList<Integer>();
        l1.add(3);
        l1.add(2);
        l1.add(4);
        l1.add(1);
        l1.sort((a, b)-> b.compareTo(a));
        System.out.println(l1);
    }

    @Test
    public void test2(){
        Person p = new Person(1, "小白");

        Class c = p.getClass();
        Field[] fs = c.getDeclaredFields();

        for (int i = 0; i < fs.length; i++) {
            try {
                //Field field = p.getClass().getDeclaredField(fs[i].getName());
                Field field = fs[i];
                field.setAccessible(true);

                try {
                    System.out.println(field.getType().getTypeName());
                    System.out.println(field.getType().getName());
                    System.out.println(field.getType().getSimpleName());
                    System.out.println(fs[i].getName()  + "===>"  + field.get(p));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

class Person{
    private Integer id;
    private String name;

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
