package com.tako.nio.reactor.single;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {

    private NIOServer(){}

    public static NIOServer Server() {
        return new NIOServer();
    }

    public void start(int port) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.select() > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    } else if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketChannel channel = (SocketChannel) key.channel();
                        int len = 0;
                        while ((len = channel.read(buffer)) > 0) {
                            buffer.flip();
                            System.out.print(new String(buffer.array(), 0 ,len));
                            buffer.clear();
                        }
                    } /*else if (key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.put("hello".getBytes());
                        buffer.flip();
                        channel.write(buffer);
                    }*/
                    it.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
