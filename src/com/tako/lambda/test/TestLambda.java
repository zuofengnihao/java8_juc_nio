package com.tako.lambda.test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * 格式一：无参数，无返回值
 * () -> {}
 *
 * 格式二：一个参数，无返回值
 * (x) -> {}
 *
 * 格式三：一个参数，小括号省略
 * x -> {}
 *
 * 格式四：多个参数，有多语句，有返回值
 * (x, y, z...) -> {
 *     ....
 *     ....
 *     ....
 * }
 *
 * 格式五：多个参数，单语句，有返回值 {} return 都可以省略
 * (x,y,z...) -> ...
 *
 * 关于参数类型 如果写就都写，不写可以省略，可通过上下文推断获得。
 *
 * lambda表达式需要函数式接口的支持
 *
 * 函数式接口：若接口中只有一个方法时，@FunctionalInterface 检查
 *
 */
public class TestLambda {

    public static void main(String[] args) {
        //匿名内部类
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };
        //lambda
        Runnable r = () -> System.out.println("hello");


        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("左风");

        Comparator<Integer> com = (var1, var2) -> {
            System.out.println("函数式接口");
            return var1 + var2;
        };

        Comparator<Integer> com1 = (var1, var2) -> var1 + var2;

    }


}
