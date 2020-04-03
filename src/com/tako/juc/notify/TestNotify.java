package com.tako.juc.notify;

/**
 * 虚假唤醒: wait() 总是在循环中使用
 */
public class TestNotify {

    public static void main(String[] args) {
        Saler saler = new Saler();
        Thread p = new Thread(new Producer(saler));
        Thread c = new Thread(new Consumer(saler));

        Thread p1 = new Thread(new Producer(saler));
        Thread c1 = new Thread(new Consumer(saler));

        p.start();
        c.start();
        p1.start();
        c1.start();
    }
}

class Producer implements Runnable {

    Saler saler;

    public Producer(Saler saler) {
        this.saler = saler;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                saler.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Saler {

    private int count = 0;

    synchronized void get() throws InterruptedException {
        while (count >= 1) {
            System.out.println("库存已满 无法进货 " + count);
            wait();
        }
        System.out.println("进货 当前库存:" + ++count);
        notifyAll();
    }

    synchronized void sale() throws InterruptedException {
        while (count <= 0) {
            System.out.println("库存已空 无法销售 " + count);
            wait();
        }
        System.out.println("卖出 当前库存:" + --count);
        notifyAll();
    }
}

class Consumer implements Runnable {

    Saler saler;

    public Consumer(Saler saler) {
        this.saler = saler;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                saler.sale();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
