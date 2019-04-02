package com.coderlong.javacore.collection.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Long Qiong
 * @create 2019/3/25
 */
public class MyIterator {
    @Test
    public void test01(){
        List<Integer> c = new ArrayList();
        c.add(1);
        c.add(3);
        c.add(2);
        c.add(1);
        c.iterator().forEachRemaining(i->{
            System.out.println(i);
        });

        c.removeIf(i->i<3);
    }



}
