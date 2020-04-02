package com.tako.java8.time;

import java.time.*;

public class TestLocalDateTime {

    public static void main(String[] args) {
        // LocalDate LocalTime LocalDateTime
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDate now1 = LocalDate.now();
        System.out.println(now1);
        LocalTime now2 = LocalTime.now();
        System.out.println(now2);

        LocalDateTime ldt = LocalDateTime.of(2020, 10, 22, 23, 40, 55);
        System.out.println(ldt);

        LocalDateTime localDateTime = now.plusYears(2);
        System.out.println(localDateTime);

        //instant 时间戳 unix元年 1970-01-01 00:00:00 到现在的毫秒值
        Instant now3 = Instant.now();//默认获取UTC时区
        System.out.println(now3);

        OffsetDateTime time = now3.atOffset(ZoneOffset.ofHours(8));
        System.out.println(time);

        System.out.println(now3.toEpochMilli());

        //Duration 计算两个时间之间的间隔
        //Period 计算两个日期之间的间隔
        Instant now4 = Instant.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant now5 = Instant.now();
        Duration between = Duration.between(now4, now5);
        System.out.println(between.toMillis());

        LocalDate date = LocalDate.now();
        LocalDate date1 = LocalDate.of(2015,5,1);
        Period period = Period.between(date1, date);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());

        //TemporalAdjuster: 时间校正器
        LocalDateTime ldt1 = LocalDateTime.now();

    }
}
