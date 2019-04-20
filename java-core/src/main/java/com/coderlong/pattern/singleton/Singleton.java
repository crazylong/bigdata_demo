package com.coderlong.pattern.singleton;

/**
 * 单例模式
 * 优点
 * 系统内存中该类只存在一个对象，节省了系统资源，对于一些需要频繁创建销毁的对象，使用单例模式可以提高系统性能。
 *
 * 缺点
 * 当想实例化一个单例类的时候，必须要记住使用相应的获取对象的方法，而不是使用new，可能会给其他开发人员造成困扰，特别是看不到源码的时候。
 *
 * 适用场合
 * 需要频繁的进行创建和销毁的对象；
 * 创建对象时耗时过多或耗费资源过多，但又经常用到的对象；
 * 工具类对象；
 * 频繁访问数据库或文件的对象。
 * @author Long Qiong
 * @create 2019/4/13
 */
public class Singleton {
    public static void main(String[] args) {
        Singleton8.INSTANCE.whateverMethod();
    }
}
//饿汉式->饱汉式
//懒汉式->饥汉式

/**
 * 1.饿汉式(静态常量)【可用】
 * 优点：写法简单，就是在类装载的时候就完成了实例化。避免了线程安全问题
 * 缺点：每次加载类都实例化，没有达到Lazy Loading效果，要是这个实例一直没有使用，造成内存浪费
 */
class Singleton1{
    private Singleton1(){}
    private final static Singleton1 INSTANCE = new Singleton1();
    public static Singleton1 getInstance(){
        return INSTANCE;
    }
}

/**
 * 2.饿汉式(静态代码块)【可用】
 * 优缺点同一
 */
class Singleton2{
    private Singleton2(){}
    private static Singleton2 INSTANCE = null;
    static {
        INSTANCE = new Singleton2();
    }
    public static Singleton2 getInstance(){
        return INSTANCE;
    }
}

/**
 * 3.懒汉式，线程不安全，不可用
 */
class Singleton3{
    private Singleton3(){}
    private static Singleton3 instance=null;
    public static Singleton3 getInstance(){
        if(instance == null){
            instance = new Singleton3();
        }
        return instance;
    }
}

/**
 * 4.懒汉式，同步方法，线程安全，不推荐用
 * 每个线程获取类实例的时候都会同步，效率低
 */
class Singleton4{
    private Singleton4(){}
    private static Singleton4 instance=null;
    public static synchronized Singleton4 getInstance(){
        if(instance == null){
            instance = new Singleton4();
        }
        return instance;
    }
}

/**
 * 5.懒汉式，同步代码块，线程不安全，不可用
 * 类似3
 */
class Singleton5{
    private Singleton5(){}
    private static Singleton5 instance=null;
    public static Singleton5 getInstance(){
        if(instance == null){
            synchronized (Singleton5.class){
                instance = new Singleton5();
            }
        }
        return instance;
    }
}

/**
 * 6.懒汉式，DCL(double checked locking) + volatile，同步代码块，线程安全，推荐用
 * happen-before原则：
 *  1 同一个线程中，书写在前面的操作happen-before书写在后面的操作
 *  2 对锁的unlock操作happen-before后续的对同一个锁的lock操作
 *  3 对volatile字段的写操作happen-before后续的对同一个字段的读操作
 * 使用volatile避免实例化的时候重排序
 */
class Singleton6{
    private Singleton6(){}
    private static volatile Singleton6 instance=null;
    public static Singleton6 getInstance(){
        if(instance == null){
            synchronized (Singleton6.class){
                if(instance == null){
                    instance = new Singleton6();
                }
            }
        }
        return instance;
    }
}

/**
 * 7.静态内部类，推荐用
 * 这种方式跟饿汉式方式采用的机制类似，但又有不同。两者都是采用了类装载的机制来保证初始化实例时只有一个线程。不同的地方在饿汉式方式是只要Singleton类被装载就会实例化，没有Lazy-Loading的作用，而静态内部类方式在Singleton类被装载时并不会立即实例化，而是在需要实例化时，调用getInstance方法，才会装载SingletonInstance类，从而完成Singleton的实例化。
 *
 * 类的静态属性只会在第一次加载类的时候初始化，所以在这里，JVM帮助我们保证了线程的安全性，在类进行初始化时，别的线程是无法进入的。
 *
 * 优点：避免了线程不安全，延迟加载，效率高。
 */
class Singleton7{
    private Singleton7(){}
    private static class Singleton7Instance{
        private static Singleton7 INSTANCE = new Singleton7();
    }
    public static Singleton7 getInstance(){
        return Singleton7Instance.INSTANCE;
    }
}

/**
 * 8. 枚举【推荐用】
 * 借助JDK1.5中添加的枚举来实现单例模式。
 * 不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。
 * 可能是因为枚举在JDK1.5中才添加，所以在实际项目开发中，很少见人这么写过。
 */
enum Singleton8{
    INSTANCE;
    public void whateverMethod() {

    }
}

/**
 * 多线程场景
 */
class Singleton9{
    private Singleton9(){}
    private static ThreadLocal<Singleton9> singleton9ThreadLocal = new ThreadLocal<>();
    public static Singleton9 getInstance(){
        Singleton9 instance = singleton9ThreadLocal.get();
        if(instance == null){
            instance = new Singleton9();
            singleton9ThreadLocal.set(instance);
        }
        return instance;
    }
}
