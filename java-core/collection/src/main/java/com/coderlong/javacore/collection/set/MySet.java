package com.coderlong.javacore.collection.set;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Long Qiong
 * @create 2019/3/26
 */
public class MySet {
    @Test
    public void test01(){
        Set s1 = new HashSet();

        Set s2 = new LinkedHashSet();

        //线程安全
        Set s3 = Collections.synchronizedSet(new HashSet<>());


    }
}
