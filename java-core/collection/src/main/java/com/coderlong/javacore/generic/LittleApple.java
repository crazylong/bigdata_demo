package com.coderlong.javacore.generic;

/**
 * 当创建了带泛型声明的接口、父类之后，可以为该接口创建实现类，或从该父类派生子类，
 * 需要指出的是，当使用这些接口、父类时不能再包含类型形参，
 * 如 下面不能用Apply<T>
 * @author Long Qiong
 * @create 2019/3/26
 */
//public class LittleApple extends Apply<T> {
//    public LittleApple(Object info) {
//        super(info);
//    }
//}

public class LittleApple extends Apple<String> {
    public LittleApple(String info) {
        super(info);
    }
}


