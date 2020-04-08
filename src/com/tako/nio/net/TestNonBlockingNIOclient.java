package com.tako.nio.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class TestNonBlockingNIOclient {

    public static void main(String[] args) {
        client();
    }

    public static void client() {
        SocketChannel socketChannel = null;
        try {
            //1. 获取通道
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));

            //2. 切换非阻塞模式
            socketChannel.configureBlocking(false);

            //3. 分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            //4. 发送数据给服务端

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                byteBuffer.put(scanner.next().getBytes());
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
