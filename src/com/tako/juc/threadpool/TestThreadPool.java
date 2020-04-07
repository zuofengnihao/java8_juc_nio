package com.tako.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高响应速度
 *
 * 结构：java.util.concurrent.Executor ： 负责线程的使用与调度的接口
 *          丨--**ExecutorService 子接口 ： 线程池的主要接口
 *                  丨--ThreadPoolExecutor 线程池的实现类
 *                  丨--ScheduledExecutorService 子接口：负责线程的调度
 *                          丨--ScheduleThreadPoolExecutor : 负责ThreadPoolExecutor 实现ScheduleExecutorService
 *
 * 工具类：Executors
 * newFixedThreadPool() ： 创建固定大小的线程池
 * newCachedThreadPool() : 创建缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量
 * newSingleThreadPool(): 创建单个线程池。
 *
 * newScheduledThreadPool() ：创建固定大小的线程，可以延迟或定时执行任务。
 *
 *
 */
public class TestThreadPool {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        /*ThreadPoolDemo demo = new ThreadPoolDemo();
        for (int i = 0; i < 10; i++) {
            pool.submit(demo);
        }*/


        Future<Integer> result = pool.submit(() -> {
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            return sum;
        });

        System.out.println(result);

        pool.shutdown();

    }

}

class ThreadPoolDemo implements Runnable {

    private int i = 0;

    @Override
    public void run() {
        while (i <= 100) {
            System.out.println(Thread.currentThread().getName() + " : " + i++);
        }
    }
}