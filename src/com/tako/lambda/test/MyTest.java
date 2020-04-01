package com.tako.lambda.test;

public class MyTest {

    public static void main(String[] args) {
        int i = MyTest.operation(5, val -> val * val);
        System.out.println(i);
    }

    public static int operation(int num, MyFun mf) {
        return mf.get(num);
    }
}
