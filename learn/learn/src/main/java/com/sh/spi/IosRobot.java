package com.sh.spi;

public class IosRobot implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I'm an IOS Robot!");
    }
}