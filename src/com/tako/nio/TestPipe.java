package com.tako.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Pipe : 是两个线程之间的单向数据连接 Pipe有一个source通道 和一个sink通道
 */
public class TestPipe {

    public static void main(String[] args) throws IOException {
        test1();
    }

    public static void test1() throws IOException {
        //1. 获取管道
        Pipe pipe = Pipe.open();

        //2. 将缓冲区中的数据写入管道
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sink = pipe.sink();
        buffer.put("通过单向管道发送数据".getBytes());
        buffer.flip();
        sink.write(buffer);

        //3. 读取缓冲区中的数据
        Pipe.SourceChannel source = pipe.source();
        buffer.flip();
        int i = source.read(buffer);
        System.out.println(new String(buffer.array(), 0, i));
    }
}
