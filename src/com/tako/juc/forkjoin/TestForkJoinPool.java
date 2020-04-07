package com.tako.juc.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoinPool {

    public static void main(String[] args) {
        test();// fork join 线程池
        test1();// 单线程
        test2();// jdk1.8 Stream 操作
    }

    public static void test() {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinSumCalculate task = new ForkJoinSumCalculate(0, 1000000000L);
        Instant start = Instant.now();
        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("fork join 耗时 : " + Duration.between(start, end).toMillis());
    }

    public static void test1() {
        Instant start = Instant.now();
        long sum = 0L;
        for (long i = 0; i <= 1000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("单线程耗时 : " + Duration.between(start, end).toMillis());
    }

    public static void test2() {
        Instant start = Instant.now();
        long sum = LongStream.rangeClosed(0L, 1000000000L).parallel().reduce(0L, Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("Long Stream 耗时 : " + Duration.between(start, end).toMillis());
    }
}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private long start, end;

    private static final long THURSHOLD = 10000L;

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = start - end;

        if (length <= THURSHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, mid);
            left.fork();
            ForkJoinSumCalculate right = new ForkJoinSumCalculate(mid + 1L, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}
