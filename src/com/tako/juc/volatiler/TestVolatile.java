package com.tako.juc.volatiler;

/**
 * volatile 可以保证可见性 不可以保证原子性
 */
public class TestVolatile {

    public static void main(String[] args) {
        Test test = new Test();
        Thread thread = new Thread(test);
        thread.start();
        while (true) {
            if (test.isFlag()) {
                System.out.println("----------------");
                break;
            }
        }
    }
}

class Test implements Runnable {

    private /*volatile*/ boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;
        System.out.println("flag=" + flag);

    }

    boolean isFlag() {
        return flag;
    }

    void setFlag(boolean flag) {
        this.flag = flag;
    }
}
