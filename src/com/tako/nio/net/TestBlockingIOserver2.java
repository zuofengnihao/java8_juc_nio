package com.tako.nio.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestBlockingIOserver2 {

    public static void main(String[] args) {
        server();
    }

    public static void server() {
        ServerSocketChannel serverSocketChannel = null;
        SocketChannel socketChannel = null;
        FileChannel fileChannel = null;
        try {
            //1. 获取通道
            serverSocketChannel = ServerSocketChannel.open();

            //2. 绑定连接
            serverSocketChannel.bind(new InetSocketAddress(8888));

            //3. 获取客户端连接的通道
            socketChannel = serverSocketChannel.accept();

            //4. 接受客户端数据并保持到本地
            fileChannel = FileChannel.open(Paths.get("C:\\Users\\admin\\Desktop\\二维码\\2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (socketChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
                byteBuffer.clear();
            }
            //5. 发送反馈给客户端
            byteBuffer.put("服务端接收数据成功".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);

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
            if (serverSocketChannel != null) {
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

