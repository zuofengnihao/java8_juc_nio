package com.tako.juc.notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序 开起3个线程 id分别为A B C 每个线程将ID打印在频幕上10便 要求输出结果顺序显示 A B C A B C...
 */
public class TestABC {

    public static void main(String[] args) {
        Printer p = new Printer("A");
        new Thread(p, "A").start();
        new Thread(p, "B").start();
        new Thread(p, "C").start();
    }

}

class Printer implements Runnable {

    private String flag;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public Printer(String flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 10; i++) {
                while (!name.equals(flag)) {
                    condition.await();
                }
                if (flag.equals("A")) flag = "B";
                else if (flag.equals("B")) flag = "C";
                else flag = "A";
                System.out.print(name);
                condition.signalAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
