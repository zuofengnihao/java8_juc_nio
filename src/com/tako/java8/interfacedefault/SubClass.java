package com.tako.java8.interfacedefault;

public class SubClass /*extends MyClass*/ implements MyFun, MyInterface {

    @Override
    public String getName() {
        return MyFun.super.getName();
    }
}
