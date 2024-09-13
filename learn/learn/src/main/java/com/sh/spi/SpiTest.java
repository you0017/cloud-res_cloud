package com.sh.spi;


import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiTest {
    public static void main(String[] args) {

        ClassLoader classLoader = SpiTest.class.getClassLoader();
        System.out.println("当前类的类加载器: " + classLoader);

        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        Iterator<Robot> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Robot robot = iterator.next();
            robot.sayHello();
            System.out.println(robot.getClass().getClassLoader());
        }
    }
}