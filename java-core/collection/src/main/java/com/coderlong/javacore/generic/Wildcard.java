package com.coderlong.javacore.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型通配符
 * @author Long Qiong
 * @create 2019/3/27
 */
public class Wildcard {
    public void test(){
        List<?> list = new ArrayList<>();
        //list.add("abc");//不能编译
        list.add(null);
    }
}
