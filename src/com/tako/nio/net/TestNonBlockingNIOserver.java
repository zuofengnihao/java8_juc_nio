package com.tako.nio.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TestNonBlockingNIOserver {

    public static void main(String[] args) {
        server();
    }

    public static void server() {
        ServerSocketChannel serverSocketChannel = null;
        try {
            //1. 获取通道
            serverSocketChannel = ServerSocketChannel.open();

            //2. 切换非阻塞
            serverSocketChannel.configureBlocking(false);

            //3. 绑定连接
            serverSocketChannel.bind(new InetSocketAddress(8888));

            //4. 获取一个选择器
            Selector selector = Selector.open();

            //5. 将通道注册到选择器上 并监听事件
            /**
             * 可用 SelectionKey 的四个常量表示:
             *  读   SelectionKey.OP_READ
             *  写               .OP_WRITE
             * 连接              .OP_CONNECT
             * 接收              .ACCEPT
             * 可用 | 预算符监控多种状态
             */
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //6. 通过选择器 轮询式的获取选择器上已经"准备就绪"的事件
            while (selector.select() > 0) {

                //7. 获取当前选择器中所有注册的选择键(已就绪的监听事件)
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    //8. 获取准备就绪的事件
                    SelectionKey key = keys.next();

                    //9. 判断具体是什么事件准备就绪
                    if (key.isAcceptable()) {
                        //10. 若接受就绪,获取客户端连接

                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //11. 切换非阻塞模式
                        socketChannel.configureBlocking(false);

                        //12. 将该通道注册到选择器上
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        //13. 获取当前选择器上"读就绪"状态的通道
                        SocketChannel channel = (SocketChannel) key.channel();

                        //14. 读取数据
                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        int len = 0;
                        while ((len = channel.read(buffer)) > 0) {
                            buffer.flip();
                            System.out.println(new String(buffer.array(), 0, len));
                            buffer.clear();
                        }
                    }

                    //15. 取消选择键
                    keys.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
