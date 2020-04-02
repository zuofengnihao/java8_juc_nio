package com.tako.java8.time;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class TestSimpleDateFormat {

    public static void main(String[] args) {
        //测试simpleDateFormat的线程安全
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ExecutorService pool = Executors.newFixedThreadPool(10);

        Callable<Date> call =
                //() -> format.parse("2020-04-02");
                () -> DateFormatThreadLocal.convert("2020-04-02");//使用ThreadLocal加锁

        //java8 time包下 都是线程安全的 final
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Callable<LocalDate> call1 = () -> LocalDate.parse("20200402", formatter);

        List<Future<Date>> result = new ArrayList<>();
        List<Future<LocalDate>> result1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //result.add(pool.submit(call));
            result1.add(pool.submit(call1));
        }
        for (Future<LocalDate> future : result1) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        pool.shutdown();

    }
}
