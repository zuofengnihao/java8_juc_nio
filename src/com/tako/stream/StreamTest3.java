package com.tako.stream;

import com.tako.lambda.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 终止操作
 */
public class StreamTest3 {

    public static void main(String[] args) {
        /**
         * 查找与匹配
         * allMatch 检查是否匹配所有元素
         * anyMatch 检查是否至少匹配一个元素
         * noneMatch 检查是否没有匹配所有元素
         * findFirst 返回第一个元素
         * findAny 返回当前流中的任意元素
         * count 返回流中的总个数
         * max 返回流中的最大值
         * min 返回流中的最小值
         */

        List<Employee> employees = Arrays.asList(
                new Employee("一", 18, 2654.32),
                new Employee("二", 23, 3200.2),
                new Employee("三", 31, 4289.13),
                new Employee("四", 44, 5743.43),
                new Employee("五", 57, 60023.33)
        );

        boolean result = employees.stream().allMatch(emp -> emp.getAge() > 30);
        System.out.println(result);

        boolean result1 = employees.stream().anyMatch(emp -> emp.getAge() > 30);
        System.out.println(result1);

        boolean result2 = employees.stream().noneMatch(emp -> emp.getAge() > 60);
        System.out.println(result2);

        Optional<Employee> first = employees.stream().sorted((e1,e2) ->  -e1.getAge().compareTo(e2.getAge())).findFirst();
        //如果为空则以括号中的对象替代
        //first.orElse()
        System.out.println(first.get());

        employees.stream().filter(emp -> emp.getAge() > 20).findAny();

        long count = employees.stream().count();
        System.out.println(count);

        Optional<Employee> max = employees.stream().max((e1, e2) -> -Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(max.get());

        Optional<Double> min = employees.stream()
                .map(Employee::getSalary)
                .min(Double::compareTo);
        System.out.println(min.get());

    }
}
