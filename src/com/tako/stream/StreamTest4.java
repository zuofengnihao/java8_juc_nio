package com.tako.stream;

import com.tako.lambda.Employee;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 终止操作
 */
public class StreamTest4 {

    public static void main(String[] args) {
        /**
         * 规约
         * reduce(T identity, BinaryOperator) / reduce(BinaryOperator)
         * 可以将流中元素结合起来,得到一个值
         */
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer reduce = list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(reduce);

        List<Employee> employees = Arrays.asList(
                new Employee("一", 18, 2654.32),
                new Employee("二", 23, 3200.2),
                new Employee("三", 31, 4289.13),
                new Employee("四", 44, 5743.43),
                new Employee("五", 57, 6023.33)
        );

        //map reduce模式
        Optional<Double> optional = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(optional.get());

        /**
         * 收集
         * collect 将流转换为其他形式 接受一个Collector接口实现 用于给Stream中元素做汇总的方法
         */
        System.out.println("==================");

        List<String> list1 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        list1.forEach(System.out::println);

        System.out.println("==================");

        Set<String> set = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());

        set.forEach(System.out::println);

        System.out.println("==================");

        HashSet<String> hashSet = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));

        hashSet.forEach(System.out::println);

        System.out.println("==================");

        Long count = employees.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        Double avgSalary = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avgSalary);

        Double sum = employees.stream().collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        Optional<Employee> op = employees.stream()
                .collect(Collectors.maxBy((e1, e2) ->
                        e1.getSalary().compareTo(e2.getSalary())
                ));
        System.out.println(op.get());

        System.out.println("==================");
        Map<String, List<Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(e -> {
                    if (e.getAge() <= 35) return "青年";
                    else if (e.getAge() <= 50) return "中年";
                    else return "老年";
                }));
        System.out.println(map);

        System.out.println("==================");
        Map<Boolean, List<Employee>> map1 = employees.stream()
                .collect(Collectors.partitioningBy(emp -> emp.getAge() > 30));
        System.out.println(map1);

        System.out.println("==================");
        IntSummaryStatistics collect = employees.stream()
                .collect(Collectors.summarizingInt(Employee::getAge));
        System.out.println(collect);

        String s = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",", "开始:", ",结束!"));
        System.out.println(s);
    }
}
