package com.coderlong.javacore.lambda;

/**
 * @author Long Qiong
 * @create 2019/3/26
 */
public class LambdaTest {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i< 10;i++){
                    System.out.println(i);
                }
            }
        };

        Runnable r2 = ()->{
            for(int i=0; i< 10;i++){
                System.out.println(i);
            }
        };

        r2.run();

        Object o = (Runnable)() -> {

        };

        FkTest fk = new FkTest() {
            @Override
            public void run(String msg) {
                for(int i=0; i< 10;i++){
                    System.out.println(i);
                }
            }
        };

        FkTest fk2 = (msg) -> {
            System.out.println(msg);
        };
        fk2.run("hi");
    }
}

@FunctionalInterface
interface FkTest{
    void run(String msg);
}
