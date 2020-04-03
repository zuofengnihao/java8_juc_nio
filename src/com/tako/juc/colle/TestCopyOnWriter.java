package com.tako.juc.colle;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriter容器 不适合写操作过多 适合并发迭代
 */
public class TestCopyOnWriter {

    public static void main(String[] args) {
        HelloThread helloThread = new HelloThread();
        for (int i = 0; i < 10; i++) {
            new Thread(helloThread).start();
        }
    }

}

class HelloThread implements Runnable {

    //private static List<String> list = Collections.synchronizedList(new ArrayList<>());//发生并发修改异常

    private static List<String> list = new CopyOnWriteArrayList();//不会发生并发修改异常 因为每次写入都会复制

    static {
        list.add("AA");
        list.add("bb");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            list.add("AA");//ConcurrentModificationException 并发修改异常
        }
    }
}