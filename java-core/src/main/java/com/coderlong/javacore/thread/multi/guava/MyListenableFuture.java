package com.coderlong.javacore.thread.multi.guava;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Qiong.Long
 * @create 2021/5/25
 */
public class MyListenableFuture {

    @Test
    public void test1(){
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("retryClient-pool-").build();

        ExecutorService exec = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5), threadFactory);




        ListeningExecutorService service = MoreExecutors.listeningDecorator(exec);

        ListenableFuture explosion = service.submit(new Callable() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "call");

                return 10;

            }

//            public Explosion call() {
//                return pushBigRedButton();
//            }
        });
        Futures.addCallback(explosion, new FutureCallback<Integer>() {
            // we want this handler to run immediately after we push the big red button!
            @Override
            public void onSuccess(@Nullable Integer o) {
                System.out.println(Thread.currentThread().getName() + "onSuccess" + o);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(Thread.currentThread().getName() + "onFailure");
                throwable.printStackTrace();
            }

//            public void onSuccess(Explosion explosion) {
//                walkAwayFrom(explosion);
//            }
//            public void onFailure(Throwable thrown) {
//                battleArchNemesis(); // escaped the explosion!
//            }
        }, service);

    }

    @Test
    public void test2(){

    }
}
