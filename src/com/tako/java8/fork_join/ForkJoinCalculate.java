package com.tako.java8.fork_join;

import java.util.concurrent.RecursiveTask;

/**
 * fork join 框架: 将一个复杂任务拆分成多个小任务
 * 工作窃取: 当一个核心闲置了会随机窃取其他核心线程队列末尾的一个线程任务来执行
 *
 * 使用: 继承RecursiveTask<T> 实现 compute() 方法
 * task.fork() 拆分 task.join() 合并
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;
    private static final long THRESHOLD = 10000;

    public ForkJoinCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            left.fork();

            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}
