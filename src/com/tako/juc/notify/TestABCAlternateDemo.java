package com.tako.juc.notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ABC题目
 */
public class TestABCAlternateDemo {

    public static void main(String[] args) {
        AlternateDemo demo = new AlternateDemo();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.loopA();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.loopB();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.loopC();
            }
        }, "C").start();
    }
}

class AlternateDemo {

    private int flag = 1;

    private Lock lock = new ReentrantLock();

    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    void loopA() {
        try {
            lock.lock();
            if (flag != 1) {
                condition1.await();
            }
            flag = 2;
            System.out.println(Thread.currentThread().getName());
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    void loopB() {
        try {
            lock.lock();
            if (flag != 2) {
                condition2.await();
            }
            flag = 3;
            System.out.println(Thread.currentThread().getName());
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    void loopC() {
        try {
            lock.lock();
            if (flag != 3) {
                condition3.await();
            }
            flag = 1;
            System.out.println(Thread.currentThread().getName());
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}