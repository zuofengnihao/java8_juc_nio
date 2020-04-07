package com.tako.juc.threadpool;

import java.util.Random;
import java.util.concurrent.*;

public class TestScheduledThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 5; i++) {
            ScheduledFuture<Integer> future = pool.schedule(() -> {
                int num = new Random().nextInt(100);
                System.out.println(Thread.currentThread().getName() + " : " + num);
                return num;
            }, 3, TimeUnit.SECONDS);// schedule(, 数值, 单位(TimeUnit))
            System.out.println(future.get());
        }
        pool.shutdown();

    }
}


