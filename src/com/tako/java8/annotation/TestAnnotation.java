package com.tako.java8.annotation;

import java.lang.reflect.Method;

public class TestAnnotation {

    public static void main(String[] args) throws NoSuchMethodException {
        Class<TestAnnotation> clazz = TestAnnotation.class;
        Method show = clazz.getMethod("show", String.class);

        MyAnnotation[] as = show.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation annotation : as) {
            System.out.println(annotation.value());
        }
    }

    @MyAnnotation("hello")
    @MyAnnotation("world")
    public void show(@MyAnnotation("think") String str) {}
}
