package com.coderlong.javacore.generic.lower;

import java.util.List;

/**
 * 通配符下限
 * @author Long Qiong
 * @create 2019/3/28
 */
public class MyUtils {
    //通配符下限
    <T> T copy(List<? super T> dest, List<T> src){
        T last = null;
        for (T t : src) {
            last = t;
            dest.add(t);
        }
        return last;
    }

    //通配符上限
    <T> T copy2(List<T> dest, List<? extends T> src){
        T last = null;
        for (T t : src) {
            last = t;
            dest.add(t);
        }
        return last;
    }
}
