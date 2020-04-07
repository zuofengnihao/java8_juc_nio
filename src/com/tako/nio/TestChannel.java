package com.tako.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Channel 通道: 用于源节点与目标节点的链接. 在Java NIO 中负责缓冲区中数据的传输.channel本身不负责存储数据
 *
 * Channel实现类:
 *        java.io.channels.Channel
 * 本地:          |-- FileChannel
 * 网络: tcp/ip   |-- SocketChannel
 *               |-- ServerSocketChannel
 *       udp     |-- DatagramChannel
 *
 * 获取Channel:
 * 1.Java 针对支持通道类提供了 getChannel() 方法
 *      本地IO:
 *      FileInputStream/FileOutputStream
 *      RandomAccessFile
 *
 *      网络IO:
 *      Socket
 *      ServerSocket
 *      DatagramSocket
 *
 * 2.在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3.在 JDK 1.7 中的 NIO.2 针对 Files 工具类的 newByteChannel()
 *
 * 通道之间的直接传输
 * transferFrom()
 * transferTo()
 *
 * 分散(Scatter)与聚集(Gather)
 * 分散读取(Scattering Reads) : 将通道中的数据分散到多个缓冲区中
 * 聚集写入(Gathering Write) : 将多个缓冲区的数据聚集到一个通道中
 *
 * 字符集: Charset
 * 编码: 字符串 -> 字节数组
 * 解码: 字节数组 -> 字符串
 *
 */
public class TestChannel {

    public static void main(String[] args) {
        /*test1();//test1 耗时 : 49
        test2();//test2 耗时 : 10
        test3();*/
        //test4();
        //test5();
        test6();
    }

    //1.利用通道完成文件的复制 (非直接缓冲区)
    public static void test1() {

        Instant start = Instant.now();

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            fis = new FileInputStream("C:\\Users\\admin\\Desktop\\二维码\\1.jpg");
            fos = new FileOutputStream("C:\\Users\\admin\\Desktop\\二维码\\2.jpg");

            //1.获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //2.分配指定大小的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //3.将通道中的数据存入缓冲区
            while (inChannel.read(buffer) != -1) {
                //4.将缓冲区中的数据写入通道
                buffer.flip();//切换成读模式
                outChannel.write(buffer);
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outChannel != null) outChannel.close();
                if (inChannel != null) inChannel.close();
                if (fos != null) fos.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Instant end = Instant.now();
            System.out.println("test1 耗时 : " + Duration.between(start, end).toMillis());
        }
    }

    //2.利用通道完成文件的复制 (直接缓冲区 内存映射文件)
    public static void test2() {

        Instant start = Instant.now();

        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            //1.获取channel
            inChannel = FileChannel.open(Paths.get("C:\\Users\\admin\\Desktop\\二维码\\1.jpg"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("C:\\Users\\admin\\Desktop\\二维码\\3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
            //内存映射文件
            MappedByteBuffer inMappedBuffer = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedBuffer = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());

            byte[] bytes = new byte[inMappedBuffer.limit()];
            inMappedBuffer.get(bytes);
            outMappedBuffer.put(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Instant end = Instant.now();
            System.out.println("test2 耗时 : " + Duration.between(start, end).toMillis());
        }
    }

    //通道之间的数据传输
    public static void test3() {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("C:\\Users\\admin\\Desktop\\二维码\\1.jpg"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("C:\\Users\\admin\\Desktop\\二维码\\4.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

            inChannel.transferTo(0, inChannel.size(), outChannel);
            outChannel.transferFrom(inChannel, 0, inChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //分散聚集
    public static void test4() {
        RandomAccessFile rw1 = null;
        RandomAccessFile rw2 = null;
        FileChannel channel1 = null;
        FileChannel channel2 = null;
        try {
            //1.获取通道
            rw1 = new RandomAccessFile("C:\\Users\\admin\\Desktop\\二维码\\1.txt", "rw");
            channel1 = rw1.getChannel();
            //2.分配指定大小的缓冲区
            ByteBuffer buf1 = ByteBuffer.allocate(100);
            ByteBuffer buf2 = ByteBuffer.allocate(1024);
            //3.分散读取
            ByteBuffer[] bufs = {buf1, buf2};
            channel1.read(bufs);

            for (ByteBuffer buffer : bufs) {
                buffer.flip();
            }

            System.out.println(new String(buf1.array(), 0, buf1.limit()));
            System.out.println("-------------------------------");
            System.out.println(new String(buf2.array(), 0, buf2.limit()));

            rw2 = new RandomAccessFile("C:\\Users\\admin\\Desktop\\二维码\\2.txt", "rw");
            channel2 = rw2.getChannel();
            //4.集中写入
            channel2.write(bufs);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channel1 != null) {
                try {
                    channel1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (channel2 != null) {
                try {
                    channel2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (rw1 != null) {
                try {
                    rw1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (rw2 != null) {
                try {
                    rw2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //字符集
    public static void test5() {
        SortedMap<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = map.entrySet();
        for (Map.Entry entry : entries) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    public static void test6() {
        try {
            Charset charset = Charset.forName("GBK");

            //获取编码器
            CharsetEncoder encoder = charset.newEncoder();
            //获取解码器
            CharsetDecoder decoder = charset.newDecoder();

            CharBuffer charBuffer = CharBuffer.allocate(1024);
            charBuffer.put("左风牛逼!");
            charBuffer.flip();

            //编码
            ByteBuffer byteBuffer = encoder.encode(charBuffer);

            for (int i = 0; i < 9; i++) {
                System.out.println(byteBuffer.get());
            }

            //解码
            byteBuffer.flip();
            CharBuffer buffer = decoder.decode(byteBuffer);
            System.out.println(buffer.toString());

            System.out.println("-----------------");

            Charset charset1 = Charset.forName("UTF-8");
            byteBuffer.flip();
            CharBuffer charBuffer1 = charset1.decode(byteBuffer);
            System.out.println(charBuffer1.toString());


        } catch (CharacterCodingException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
