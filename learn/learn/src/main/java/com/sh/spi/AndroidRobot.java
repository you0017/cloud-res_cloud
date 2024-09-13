package com.sh.spi;

public class AndroidRobot implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I'm an Android Robot!");
    }
}