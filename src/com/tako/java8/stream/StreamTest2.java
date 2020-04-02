package com.tako.java8.stream;

import com.tako.java8.lambda.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 中间操作
 */
public class StreamTest2 {

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
         * 映射:
         *  map 接受lambda 将元素转换成其他形式或提取信息 接收一个函数作为参数 该函数会被应用到每个元素上 并将其映射成一个新的元素
         *  flatMap 接收一个函数作为参数 将流中的每个值都换成另一个流 然后把所有流连接成一个流
         */

        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        list.stream()
                .map(str -> str.toUpperCase())
                .forEach(System.out::println);

        employees.stream()
                .map(employee -> employee.getAge())
                .forEach(System.out::println);

        System.out.println("=========================");

        Stream<Stream<Character>> streamStream = list.stream()
                .map(StreamTest2::filterCharacter);
        streamStream.forEach(st -> st.forEach(System.out::println));//{{a,a,a},{b,b,b}}

        System.out.println("============================");
        list.stream().flatMap(StreamTest2::filterCharacter).forEach(System.out::println);//{a,a,a,b,b,b}

        System.out.println("==============================");
        /**
         * 排序:
         * sorted() 自然排序
         * sorted(Comparator) 定制排序
         */
        List<String> list1 = Arrays.asList("ccc", "aaa", "dddd", "bbbbbb");
        list1.stream().sorted().forEach(System.out::println);

        System.out.println("==============================");

        employees.stream()
                .sorted((x,y) -> {
                    if (x.getAge().equals(y.getAge())) {
                        return x.getSalary().compareTo(y.getSalary());
                    } else {
                        return - x.getAge().compareTo(y.getAge());
                    }
                })
                .forEach(System.out::println);
    }

    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }
}
