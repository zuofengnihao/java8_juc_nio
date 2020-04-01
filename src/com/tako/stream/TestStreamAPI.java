package com.tako.stream;

import com.tako.lambda.Employee;

import java.util.Arrays;
import java.util.List;

public class TestStreamAPI {

    public static void main(String[] args) {
        /*
            给定 [1,2,3,4,5] 返回平方 [1,4,9,16,25]
         */
        Integer[] integers = {1,2,3,4,5};
        Arrays.stream(integers).map(i -> i * i).forEach(System.out::println);

        /*
            用map 和 reduce 方法数一数流中有多少个Employee
         */
        List<Employee> employees = Arrays.asList(
                new Employee("一", 18, 2654.32),
                new Employee("二", 23, 3200.2),
                new Employee("三", 31, 4289.13),
                new Employee("四", 44, 5743.43),
                new Employee("五", 57, 6023.33)
        );
        System.out.println("======================");
        Integer reduce = employees.stream()
                .map(e -> 1)
                .reduce(0, Integer::sum);
        System.out.println(reduce);
    }
}
