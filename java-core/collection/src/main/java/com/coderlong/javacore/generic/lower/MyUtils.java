package com.coderlong.javacore.generic.lower;

import java.util.List;

/**
 * 通配符下限
 * @author Long Qiong
 * @create 2019/3/28
 */
public class MyUtils {
    <T> T copy(List<? super T> dest, List<T> src){
        T last = null;
        for (T t : src) {
            last = t;
            dest.add(t);
        }
        return last;
    }
}
