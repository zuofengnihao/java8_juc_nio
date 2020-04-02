package com.tako.java8.fork_join;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;

/**
 * 使用并不多
 */
public class TestForkJoin {

    public static void main(String[] args) {

        Instant b = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinCalculate task = new ForkJoinCalculate(0, 100000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);

        Instant e = Instant.now();

        System.out.println("耗时:" + Duration.between(b, e).toMillis());


        Instant begin = Instant.now();

        long num = 0;
        for (long i = 0; i <= 100000000L; i++) {
            num += i;
        }
        System.out.println(num);

        Instant end = Instant.now();

        System.out.println("耗时:" + Duration.between(begin, end).toMillis());
    }
}
