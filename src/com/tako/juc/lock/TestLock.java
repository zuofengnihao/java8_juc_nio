package com.tako.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

    public static void main(String[] args) {
        LockDemo lockDemo = new LockDemo();
        for (int i = 0; i < 3; i++) {
            new Thread(lockDemo, "窗口" + (i + 1)).start();
        }
    }

}

class LockDemo implements Runnable {

    private int tick = 100;

    ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(200);
                lock.lock();
                if (tick > 0)
                    System.out.println(Thread.currentThread().getName() + "售出一张票 剩余票数:" + --tick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}