package com.sh.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Predicates {
    public static void main(String[] args) {
        List<String> ns = Arrays.asList("Java", "Python", "C++");  // 不可变集合
        ArrayList names = new ArrayList(ns);
//        Predicate<String> predicate = (s) -> s.length() > 3;

        /**
         * test使用
         */
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() > 3;
            }
        };

        boolean b = names.removeIf(predicate);
        System.out.println(names);

        /**
         * and 使用
         */
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        Predicate<Integer> predicate1 = (x)-> x > 2;
        Predicate<Integer> predicate2 = (x)-> x < 5;
        List<Integer> collect = list.stream().filter(predicate1.and(predicate2)).collect(Collectors.toList());
        System.out.println(collect);

        /**
         * or 使用
         */
        List<Integer> list1 = list.stream().filter(predicate1.or(predicate2)).collect(Collectors.toList());
        System.out.println(list1);

        /**
         * negate使用  否定
         */
        List<String> list2 = Arrays.asList("jva", "javaee", "javamail", "javame");
        Predicate<String> predicate3 = (x)-> x.length() > 4;
        List<String> collect1 = list2.stream().filter(predicate3.negate()).collect(Collectors.toList());
        System.out.println(collect1);

    }
}
