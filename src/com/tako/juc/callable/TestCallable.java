package com.tako.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 */
public class TestCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableDemo demo = new CallableDemo();
        FutureTask<String> task = new FutureTask(demo);
        new Thread(task).start();
        System.out.println("线程开始执行....等待结果中");
        String s = task.get();
        System.out.println(s);
    }

}

class CallableDemo implements Callable<String> {

    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }
        return "hello world" + sum;
    }
}
