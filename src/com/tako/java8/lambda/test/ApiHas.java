package com.tako.java8.lambda.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * java8 内置的四大核心函数接口
 *
 * Consumer<T> : 消费型接口
 *      void accept(T t);
 *
 * Supplier<T> : 供给型接口
 *      T get();
 *
 * Function<T, R> : 函数型接口
 *      R apply(T, t);
 *
 * Predicate<T> : 断言型接口
 *      boolean test(T t);
 *
 * 其他接口：
 * BiFunction<T, U, R>
 *      R apply(T t, U u);
 *
 * UnaryOperator<T>
 *     T apply(T t);
 *
 * BinaryOperator<T>
 *     T apply(T t1, T t2);
 *
 * BiConsumer<T, U>
 *     void accept(T t, U u);
 *
 * ToIntFunction<T>
 *     int apply(T t);
 * ToLongFunction<T>
 *     long apply(T t);
 * ToDoubleFunction<T>
 *     double apply(T t);
 *
 * IntFunction<R>
 * LongFunction<R>
 * DoubleFunction<R>
 *
 */
public class ApiHas {

    public static void main(String[] args) {
        //Consumer
        ApiHas.happy(10000, m -> System.out.println("消费了" + m + "元"));

        //Supplier
        List<Integer> list = ApiHas.getNumList(10, () -> (int)(Math.random() * 100));
        System.out.println(list);

        //Function
        String s = getStr("\t\t\t   hello  ", str -> str.trim());
        System.out.println(s);

        String str = getStr("左风", var -> var.substring(0,1));
        System.out.println(str);

        //Predicate
        List<String> strings = getList(Arrays.asList("hello", "haha", "world", "zuofeng", "nihao", "heihei", "hehe", "wow"), val -> val.length() > 4);
        System.out.println(strings);

    }

    //消费型
    public static void happy(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    //供给型
    public static List<Integer> getNumList(int num, Supplier<Integer> supplier) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            integers.add(supplier.get());
        }
        return integers;
    }

    //函数型
    public static String getStr(String str, Function<String, String> function) {
        return function.apply(str);
    }

    //断言型
    public static List<String> getList(List<String> strings, Predicate<String> predicate) {
        ArrayList<String> list = new ArrayList<>();
        for (String str : strings) {
            if (predicate.test(str)) list.add(str);
        }
        return list;
    }
}
