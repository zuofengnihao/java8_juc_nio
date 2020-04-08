package com.tako.nio.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.Instant;
import java.util.Scanner;

public class TestNonBlockingNIOclient2 {

    public static void main(String[] args) {
        try {
            //1. 获取管道
            DatagramChannel datagramChannel = DatagramChannel.open();
            //2. 设置非阻塞
            datagramChannel.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String content = scanner.next();
                buffer.put((Instant.now() + "\t" + content).getBytes());
                buffer.flip();
                datagramChannel.send(buffer, new InetSocketAddress("127.0.0.1", 8888));
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
