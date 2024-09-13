package com.sh.限流算法.计数器限流算法;

public class RateLimiterManualTest {
    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateLimiter(3); // 每秒允许 3 个请求

        // 测试在时间窗口内的请求
        System.out.println("测试在时间窗口内的请求：");
        for (int i = 0; i < 3; i++) {
            boolean result = rateLimiter.allowRequest();
            System.out.println("请求 " + (i + 1) + " 是否被允许：" + result);
        }

        // 第四个请求应该被拒绝
        boolean fourthRequestResult = rateLimiter.allowRequest();
        System.out.println("第四个请求是否被允许：" + fourthRequestResult);

        // 等待时间窗口过期
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 新时间窗口下的第一个请求应该被允许
        boolean newRequestResult = rateLimiter.allowRequest();
        System.out.println("新时间窗口下的第一个请求是否被允许：" + newRequestResult);
    }
}