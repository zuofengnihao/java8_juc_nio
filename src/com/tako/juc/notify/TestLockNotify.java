package com.tako.juc.notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLockNotify {

    public static void main(String[] args) {
        SalerLock saler = new SalerLock();
        ProducerLock p = new ProducerLock(saler);
        ConsumerLock c = new ConsumerLock(saler);

        ProducerLock p1 = new ProducerLock(saler);
        ConsumerLock c1 = new ConsumerLock(saler);

        new Thread(p).start();
        new Thread(c).start();
        new Thread(p1).start();
        new Thread(c1).start();
    }
}


class ProducerLock implements Runnable {

    SalerLock saler;

    public ProducerLock(SalerLock saler) {
        this.saler = saler;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                saler.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class SalerLock {

    private int count = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    void get() {
        try {
            lock.lock();
            while (count >= 1) {
                System.out.println("库存已满 无法进货 " + count);
                condition.await();
            }
            System.out.println("进货 当前库存:" + ++count);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void sale() {
        try {
            lock.lock();
            while (count <= 0) {
                System.out.println("库存已空 无法销售 " + count);
                condition.await();
            }
            System.out.println("卖出 当前库存:" + --count);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class ConsumerLock implements Runnable {

    SalerLock saler;

    public ConsumerLock(SalerLock saler) {
        this.saler = saler;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                saler.sale();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}