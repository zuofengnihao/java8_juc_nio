package com.tako.java8.op;

import com.tako.java8.lambda.Employee;

import java.util.Optional;

/**
 * Optional容器:减少空指针异常
 *
 *  常用方法:
 *  of(T t) 创建一个Optional实例
 *  empty() 创建一个空的实例
 *  ofNullable(T t) 若t不为null 创建Optional实例 否则创建空实例
 *  isPresent() 判断是否有值
 *  orElse(T t) 如果调用对象包含值 返回该值 否则返回t
 *  orElseGet(Supplier s) 如果调用对象包含值 返回值 否则返回s获取的值
 *  map(Function f) 如果有值对其处理 并返回处理后的 Optional 否则返回Optional.empty()
 *  flatMap(Function f) 与map类似 要求返回值必须是Optional
 */
public class TestOptional {

    public static void main(String[] args) {
        Optional<Employee> op = Optional.of(new Employee());
        Employee employee = op.get();
        System.out.println(employee);

        Optional<Employee> empty = Optional.empty();
        //System.out.println(empty.get());

        Optional<Employee> optionalEmployee = Optional.ofNullable(new Employee());
        if (optionalEmployee.isPresent()) {
            System.out.println(optionalEmployee.get());
        } else {
            System.out.println("没值");
        }
    }
}
