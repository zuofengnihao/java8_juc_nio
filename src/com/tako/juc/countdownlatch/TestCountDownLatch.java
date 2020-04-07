package com.tako.juc.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * countDownLatch 闭锁
 * 构造CountDownLatch(int n)
 * n为倒数数字的起始值 当n个线程执行 countDown()方法后 await()线程开始执行
 */
public class TestCountDownLatch {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        LatchDemo latchDemo = new LatchDemo(countDownLatch);

        long begin = System.currentTimeMillis();

        for (int i = 0; i < 5 ; i++) {
            new Thread(latchDemo).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("\n" + "耗时 : " + (end - begin));
    }
}

class LatchDemo implements Runnable {

    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 50000; i++) {
                if (i % 2 == 0) {
                    if (i == 0)  {
                        System.err.print("\n" + i + " ");
                    } else
                        System.out.print(i + " ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }
}
