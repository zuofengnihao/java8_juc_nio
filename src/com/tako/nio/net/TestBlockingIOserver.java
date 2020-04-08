package com.tako.nio.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 使用 NIO 完成网络通讯的三个核心 :
 *
 *  1. 通道(Channel) : 负责连接
 *
 *      java.nio.channels.Channel 接口:
 *           |--SelectableChannel
 *                  |--SocketChannel
 *                  |--ServerSocketChannel
 *                  |--DatagramChannel
 *
 *                  |-Pipe.SinkChannel
 *                  |--Pipe.SourceChannel
 *
 *  2. 缓冲区(Buffer) : 负责数据的存取
 *
 *  3. 选择器(Selector) : 是 SelectableChannel 的多路复用器.用于监控SelectableChannel 的 IO 状况
 *
 */
public class TestBlockingIOserver {

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
