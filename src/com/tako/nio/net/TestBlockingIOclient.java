package com.tako.nio.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestBlockingIOclient {

    public static void main(String[] args) {
        client();
    }

    public static void client() {
        SocketChannel socketChannel = null;
        FileChannel fileChannel = null;

        try {
            //1. 获取通道
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));

            //2. 分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            //3. 读取本地文件 并发送到客户端
            fileChannel = FileChannel.open(Paths.get("C:\\Users\\admin\\Desktop\\二维码\\1.jpg"), StandardOpenOption.READ);

            while (fileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
