package com.tako.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 三个操作步骤:
 * 1.创建stream
 * 2.中间操作
 * 3.终止操作
 */
public class StreamTest {

    public static void main(String[] args) {
        //1.Collection系列集合提供的stream()(串行)或parallelStream()(并行)
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();

        //2.通过Arrays中的静态方法stream()获取
        String[] strs = new String[10];
        Stream<String> stream1 = Arrays.stream(strs);

        //3.通过Stream类的静态方法of()
        Stream<String> stream2 = Stream.of("a","bbb","cccc");

        //4.创建无限流
        //迭代
        Stream<Integer> stream3 = Stream.iterate(0, x -> x + 2);
        stream3.limit(10).forEach(System.out::println);

        //生成
        Stream<Double> stream4 = Stream.generate(() -> Math.random());
        stream4.limit(10).forEach(System.out::println);

    }
}
