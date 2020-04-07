package com.tako.nio;

import java.nio.ByteBuffer;

/**
 * Buffer 缓冲区: 在JAVA NIO中负责数据的(不同数据类型)存取 底层就是数组
 *
 * 提供了相应类型的缓冲区(boolean除外):
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *
 * 通过allocate()获取缓冲区
 *
 * 存取数据的核心方法:
 * put() 存入数据到缓冲区
 * get() 获取缓冲区中的数据
 *
 * 缓冲区中的4个核心属性:
 * capacity : 容量,表示缓冲区中最大存储容量.一旦声明不能改变
 * limit : 界限,表示缓冲区中可以操作数据的大小.limit后面的数据不能读写
 * position : 位置,表示缓冲区中正在操作数据的位置
 *
 * mark : 标记,表示记录当前position的位置.可以通过reset()恢复到 mark 的位置
 *
 * 0<= mark <= position <= limit <= capacity
 *
 * 直接缓冲区与非直接缓冲区
 * 非直接缓冲区 : 通过 allocate() 方法分配缓冲区,将缓冲区建立在JVM的内存中
 * 直接缓冲区: 通过 allocateDirect() 方法分配直接缓冲区,将缓冲去建立在物理内存中.可以提高效率
 *
 * isDirect() 判断是否为直接缓冲区
 */
public class TestBuffer {

    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        String str = "abcde";

        //1.分配一个指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        System.out.println("-----------------allocate()------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //2.利用put()方法存入数据到缓冲区中
        buffer.put(str.getBytes());

        System.out.println("-----------------put()------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //3.利用flip()方法进入读数据模式
        buffer.flip();

        System.out.println("-----------------filp()------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //4.利用get()获取数据
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        System.out.println(new String(bytes, 0, bytes.length));

        System.out.println("-----------------get()------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //5.利用rewind() 可重复读
        buffer.rewind();

        System.out.println("-----------------rewind()------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //6.利用clear() 情况缓冲区(缓冲区中的数据依然存在,只回复所有指针的状态)
        buffer.clear();

        System.out.println("-----------------clear()------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        System.out.println((char) buffer.get());

    }

    public static void test2() {
        String str = "abcde";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes, 0, 2);
        System.out.println(new String(bytes, 0, 2));
        System.out.println(buffer.position());
        //mark 标记
        buffer.mark();
        buffer.get(bytes, 2, 3);
        System.out.println(new String(bytes, 2, 3));
        System.out.println(buffer.position());
        //reset() : position回复到标记位置
        buffer.reset();
        System.out.println(buffer.position());
        //判断缓冲区是否还有可操作的数据
        if(buffer.hasRemaining())
            //获取缓冲区中可以操作数据的数量
            System.out.println(buffer.remaining());
    }
}
