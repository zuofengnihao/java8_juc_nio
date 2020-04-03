package com.tako.juc.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 ReadWriteLock
 *
 * 写写互斥 读写互斥 读读共享
 */
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteDemo demo = new ReadWriteDemo();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> demo.get(), "Read" + i).start();
        }

        new Thread(() -> demo.set((int) (Math.random() * 101)), "Write:").start();
    }
}

class ReadWriteDemo {
    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void get() {
        lock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + " : " + number);
        lock.readLock().unlock();
    }

    public void set(int number) {
        lock.writeLock().lock();
        System.out.println(Thread.currentThread().getName());
        this.number = number;
        lock.writeLock().unlock();
    }
}
