package com.tako.juc.volatiler;

/**
 * i++ 原子性问题
 * int i=10;
 * i=i++//10
 *
 * int temp = i;
 * i = i + 1;
 * i = temp;
 *
 * CAS compare and swap
 */
public class TestAtomicDemo {

    public static void main(String[] args) {
        AtomicDemo demo = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(demo).start();
        }
    }
}

class AtomicDemo implements Runnable {

    //volatile无法保证原子性 应该使用Atomic原子变量
    private volatile int num = 0;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(getNum());
    }

    public int getNum() {
        return num++;
    }

}
