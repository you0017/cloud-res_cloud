package com.sh.限流算法.计数器限流算法;

public class CounterTest {
    public static void main(String[] args) {
        Counter counter = new Counter();

        // 测试在时间窗口内的请求
        System.out.println("测试在时间窗口内的请求：");
        for (int i = 0; i < 300; i++) {
//            boolean result = counter.limit();
//            System.out.println("Request " + (i + 1) + " result: " + result);
        }

        // 测试超出时间窗口后的请求
        System.out.println("\n测试在时间窗口过期后的请求：");
        try {
            Thread.sleep(3100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        boolean newRequestResult = counter.limit();
//        System.out.println("时间窗口过期后的新请求结果：" + newRequestResult);
    }
}