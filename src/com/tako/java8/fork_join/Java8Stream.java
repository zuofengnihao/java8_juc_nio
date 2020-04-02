package com.tako.java8.fork_join;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

/**
 * java8并行流
 */
public class Java8Stream {
    public static void main(String[] args) {

        Instant begin = Instant.now();

        long sum = LongStream
                .rangeClosed(0, 100000000L)
                .parallel()
                .reduce(0, Long::sum);

        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗时:" + Duration.between(begin, end).toMillis());
    }
}
