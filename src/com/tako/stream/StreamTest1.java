package com.tako.stream;

import com.tako.lambda.Employee;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 中间操作
 */
public class StreamTest1 {

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("一", 18, 2654.32),
                new Employee("二", 23, 3200.2),
                new Employee("三", 31, 4289.13),
                new Employee("四", 44, 5743.43),
                new Employee("五", 57, 60023.33),
                new Employee("五", 57, 60023.33),
                new Employee("五", 57, 60023.33)
        );

        /**
         * 筛选与切片
         * filter 接受lambda 从流中排除某些元素
         * limit 截断流 使其元素不超过给定值
         * skip(n) 跳过元素 返回一个抛弃n个元素的流 若不足n个 返回一个空流 与limit(n)互补
         * distinct 筛选 通过流生成元素的hashcode()和equals()去出重复元素
         */


        //中间操作:没有出现终止操作时不会执行
        Stream<Employee> stream = employees.stream().filter(employee -> {
            System.out.println("Stream API 的中间操作");
            return employee.getAge() > 30;
        });
        //终止操作:一次性执行全部中间操作+最后的中间操作
        stream.forEach(System.out::println);
        //多个中间操作可以连接起来形成一个流水线,除非流水线上触发终止操作,否则中操作不会执行任何处理,而在终止操作时一次性全部处理,称为"惰性求值"

        //内部迭代:Stream API完成 已上运行结果中迭代方式就是内部迭代

        System.out.println("=========================");

        //外部迭代:
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            System.out.println(employee);
        }

        System.out.println("===============================");
        employees.stream()
                .filter(employee -> {
                    System.out.println("短路"); // && ||
                    return employee.getSalary() < 5000;
                })
                .limit(2)
                .forEach(System.out::println);

        System.out.println("===============================");
        employees.stream()
                .skip(2)
                .forEach(System.out::println);

        System.out.println("===============================");
        employees.stream().distinct().forEach(System.out::println);

    }
}
