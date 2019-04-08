package com.coderlong.javacore.lambda;

/**
 * @author Long Qiong
 * @create 2019/3/26
 */
public class LambdaQs {
    interface Eatable{
        void taste();
    }

    interface Flyable{
        void fly(String weather);
    }

    interface Addable{
        int add(int a, int b);
    }

    public void eat(Eatable e){
        e.taste();
    }

    public void drive(Flyable f){
        System.out.println("我正在驾驶：" + f);
        f.fly("碧空如洗的晴天！");
    }

    public void test(Addable a){
        System.out.println("总和=" + a.add(3, 5));
    }

    public static void main(String[] args) {
        LambdaQs lambdaQs = new LambdaQs();
        lambdaQs.eat(()-> System.out.println("可以吃！"));

        lambdaQs.drive(weather ->{
            System.out.println("天气是：" + weather);
            System.out.println("直升机平稳飞行!");
        });

        lambdaQs.test((a, b)-> a+ b);
    }
}
