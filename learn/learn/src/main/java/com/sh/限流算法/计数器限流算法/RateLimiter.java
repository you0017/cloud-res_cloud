package com.sh.限流算法.计数器限流算法;

import java.util.concurrent.TimeUnit;

public class RateLimiter {

    private long lastRequestTime;   // 上次请求时间
    private int requestsPerSecond;   // 每秒请求数
    private int count;

    public RateLimiter(int requestsPerSecond) {
        this.requestsPerSecond = requestsPerSecond;   // 设置每秒请求数
        lastRequestTime = System.currentTimeMillis();  // 初始化上次请求时间
    }

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();   // 获取当前时间
        //  一个请求过来，如果当前时间离上次请求时间超过一秒，重置计数器
        if (now - lastRequestTime > TimeUnit.SECONDS.toMillis(1)) {
            // 超过一秒，重置计数器
            count = 0;
            lastRequestTime = now;
        }
        // 如果计数器小于每秒请求数，则允许请求
        if (count < requestsPerSecond) {
            count++;
            return true;
        }
        return false;
    }
}