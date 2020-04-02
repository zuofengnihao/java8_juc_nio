package com.tako.java8.interfacedefault;

public interface MyInterface {

    default String getName() {
        return "呵呵";
    }

    static void show() {
        System.out.println("接口中的静态方法");
    }
}
