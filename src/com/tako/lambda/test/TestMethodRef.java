package com.tako.lambda.test;

import com.tako.lambda.Employee;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 方法引用：lambda体中的内容已经实现
 *
 * 三种格式：
 *  对象::实例方法名
 *  类::静态方法名
 *  类::实例方法名(注意：第一个参数是实例方法的调用者，第二个参数是实例方法的参数时)
 *
 * 注意：调用方法的参数列表与返回类型要与函数接口中的参数列表与返回类型一致
 *
 * 构造器应用
 *  CLassName::new
 *
 */
public class TestMethodRef {

    public static void main(String[] args) {
        //对象::实例方法名
        Consumer<String> consumer = str -> System.out.println(str);
        Consumer consumer1 = System.out::println;
        consumer1.accept("zuofeng");

        Employee employee = new Employee("zuofeng", 32, 5000.0);
        Supplier<String> supplier = () -> employee.getName();
        System.out.println(supplier.get());

        Supplier<Integer> supplier1 = employee::getAge;
        System.out.println(supplier1.get());

        //类::静态方法名
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> comparator1 = Integer::compare;

        //类::实例方法
        BiPredicate<String, String> biPredicate = (x , y) -> x.equals(y);
        BiPredicate<String, String> biPredicate1 = String::equals;

        //构造器引用
        Supplier<Employee> sup = () -> new Employee();
        sup.get();
        Supplier<Employee> sup1 = Employee::new;
    }
}
