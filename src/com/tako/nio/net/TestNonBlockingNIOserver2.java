package com.tako.nio.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class TestNonBlockingNIOserver2 {

    public static void main(String[] args) {
        try {
            //1. 获取通道
            DatagramChannel datagramChannel = DatagramChannel.open();
            //2. 设置非阻塞
            datagramChannel.configureBlocking(false);
            //3. 绑定通道
            datagramChannel.bind(new InetSocketAddress(8888));
            //4. 创建选择器
            Selector selector = Selector.open();

            datagramChannel.register(selector, SelectionKey.OP_READ);

            while (selector.select() > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();

                    if(key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        datagramChannel.receive(buffer);
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, buffer.limit()));
                        buffer.clear();
                    }
                }
                it.remove();
            }
            datagramChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
