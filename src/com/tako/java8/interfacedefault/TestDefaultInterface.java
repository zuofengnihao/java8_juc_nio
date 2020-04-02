package com.tako.java8.interfacedefault;

/**
 * 类优先原则: 方法冲突类优先
 */
public class TestDefaultInterface {

    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        System.out.println(subClass.getName());
        MyInterface.show();
    }
}
