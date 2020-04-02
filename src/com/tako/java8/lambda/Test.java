package com.tako.java8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
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

        List<Employee> employees2 = Test.filterEmployee(list);
        for (Employee emp : employees2) {
            System.out.println(emp);
        }

        System.out.println("-------");

        List<Employee> employees3 = Test.filterEmp(list);
        for (Employee emp : employees3) {
            System.out.println(emp);
        }

        System.out.println("=============================");

        List<Employee> employees = Test.filterEmployee(list, new FilterEmployeesByAge());
        for (Employee emp : employees) {
            System.out.println(emp);
        }

        System.out.println("--------");

        List<Employee> employees1 = Test.filterEmployee(list, new FilterEmployeesBySalary());
        for (Employee emp : employees1) {
            System.out.println(emp);
        }

        //匿名内部类
        System.out.println("匿名内部类=======");

        List<Employee> employees4 = Test.filterEmployee(list, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() <= 5000;
            }
        });

        for (Employee employee : employees4) {
            System.out.println(employee);
        }
        
        //lambda表达式
        System.out.println("lambda表达式========");

        List<Employee> employees5 = Test.filterEmployee(list, employee -> employee.getSalary() >= 5000);
        employees5.forEach(System.out::println);

        //stream API
        System.out.println("stream API=========");

        list.stream()
                .filter(employee -> employee.getSalary() >= 5000)
                .limit(2)
                .forEach(System.out::println);
    }

    public static List<Employee> filterEmployee(List<Employee> list) {
        List<Employee> employees = new ArrayList<>();
        for (Employee emp: list) {
            if (emp.getAge() >= 35) employees.add(emp);
        }
        return employees;
    }

    public static List<Employee> filterEmp(List<Employee> list) {
        List<Employee> employees = new ArrayList<>();
        for (Employee emp: list) {
            if (emp.getSalary() >= 5000) employees.add(emp);
        }
        return employees;
    }

    //优化方法一：策略设计模式
    public static List<Employee> filterEmployee(List<Employee> list, MyPredicate<Employee> filter) {
        ArrayList<Employee> employees = new ArrayList<>();
        for (Employee employee : list) {
            if (filter.test(employee)) employees.add(employee);
        }
        return employees;
    }

    //匿名内部类

}
