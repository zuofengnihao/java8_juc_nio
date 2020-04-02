package com.tako.java8.lambda.test;

import com.tako.java8.lambda.Employee;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<Employee> list = Arrays.asList(
                new Employee("张三", 18, 9999.99),
                new Employee("李四", 28, 7777.72),
                new Employee("赵六", 38, 3333.22),
                new Employee("刘一", 48, 5555.12),
                new Employee("陈二", 58, 8855.34)
        );

        Collections.sort(list, (var1, var2) -> {
            if (var1.getAge() == var2.getAge()) {
                return var1.getName().compareTo(var2.getName());
            } else {
                return Integer.compare(var1.getAge(), var2.getAge());
            }
        });
        System.out.println(list);
    }
}
