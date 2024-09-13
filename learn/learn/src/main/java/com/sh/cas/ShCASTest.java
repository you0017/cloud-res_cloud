package com.sh.cas;

import java.util.Random;

/**
 * @program: thread
 * @description: CAS学习
 * @author: jym
 * @create: 2020/01/28
 */
public class ShCASTest {
    public static void main(String[] args) {
        final ShCAS jymCAS = new ShCAS();
        for (int i =0;i<10;i++){
            new Thread(new Runnable() {
                public void run() {
                    //获取内存值
                    int expectValue = jymCAS.getValue();
                    System.out.println(expectValue);
                    boolean flag = jymCAS.compilerAndSet(expectValue, new Random().nextInt(100));
                    System.out.println(flag);
                }
            }).start();
        }
    }
}

class ShCAS {
    private int value;

    public int getValue() {
        return value;
    }

    public synchronized int compilerAndSwap(int expectValue ,int newValue){
        int oldValue = this.value;
        // 内存值与预期值一样，进行赋值操作
        if(oldValue==expectValue){
            this.value = newValue;
        }
        return oldValue;
    }

    // 当预期值与内存值相等，说明赋值成功了
    public boolean compilerAndSet(int expectValue ,int newValue){
        return expectValue==compilerAndSwap(expectValue,newValue);
    }
}
