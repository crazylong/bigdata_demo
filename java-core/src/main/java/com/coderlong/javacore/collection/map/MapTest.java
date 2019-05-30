package com.coderlong.javacore.collection.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Long Qiong
 * @create 2019/4/18
 */
public class MapTest {
    public void testMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("java", "100");

        Hashtable ht = new Hashtable();

    }

    @Test
    public void test01(){

        System.out.println(1 << 4);
        System.out.println(1 << 30);
    }
}
