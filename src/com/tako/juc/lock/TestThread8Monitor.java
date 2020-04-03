package com.tako.juc.lock;

/**
 * 判断 打印 one or two
 *
 * 1. 两个普通同步方法 两个线程标准打印 打印// one two
 * 2. 新增Thread.sleep方法 给getOne方法 打印// two one
 * 3. 新增普通方法 getThree() 打印// three one two
 * 4. 两个普通同步方法 两个对象 打印// two one
 * 5. 修改getOne() 静态同步方法 打印// two one
 * 6. 修改getTwo() 同时为静态方法 打印// one two
 * 7. 一个静态同步 一个普通同步 两个对象 打印// two one
 * 8. 两个静态同步 两个对象 打印// one two
 *
 * 类锁: synchronized(this){}
 *
 * 对象锁: synchronize(Class){}
 *
 */
public class TestThread8Monitor {

    public static void main(String[] args) {
        Number number = new Number();
        Number number1 = new Number();

        new Thread(() -> {
            number.getOne();
        }).start();

        new Thread(() -> {
            number1.getTwo();
        }).start();

        /*new Thread(() -> {
            number.getThree();
        }).start();*/
    }
}

class Number {

    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }
}
