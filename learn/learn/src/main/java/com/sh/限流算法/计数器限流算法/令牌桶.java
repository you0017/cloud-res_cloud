package com.sh.限流算法.计数器限流算法;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 漏斗有一个进水口 和 一个出水口，出水口以一定速率出水，并且有一个最大出水速率：
 *
 * 在漏斗中没有水的时候，
 *
 * 如果进水速率小于等于最大出水速率，那么，出水速率等于进水速率，此时，不会积水
 * 如果进水速率大于最大出水速率，那么，漏斗以最大速率出水，此时，多余的水会积在漏斗中
 * 在漏斗中有水的时候
 *
 * 出水口以最大速率出水
 * 如果漏斗未满，且有进水的话，那么这些水会积在漏斗中
 * 如果漏斗已满，且有进水的话，那么这些水会溢出到漏斗之外
 */

public class 令牌桶 {//extends LimitAdaptor {
    //桶的容量
    private static final int capacity = 100;
    //令牌生成速度rate /s
    private static final int rate = 50;
    //当前令牌数量
    private volatile static AtomicInteger token = new AtomicInteger(0);

    /**
     * 开启一个线程固定频率放入令牌rate/s
     */
    private static void productToken(){
        //创建ScheduleThreadPool，参数为线程池大小
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

        //定时调度
        scheduledThreadPool.scheduleAtFixedRate(()->{
            //计算牌数
            int allTokens = token.get() + rate;
            //设置当前令牌数
            token.set(Math.min(allTokens, capacity));
        }, 0, 1, TimeUnit.SECONDS);//每隔一秒生成五个令牌
    }

    /**
     * 是否限流
     */
    private static synchronized boolean isLimit(int requestCount){
        if (token.get() < requestCount){
            //拒绝请求
            return true;
        }else {
            token.addAndGet(-requestCount);
            System.out.println("剩余令牌数："+token);
            return false;
        }
    }

    public static void main(String[] args) {
        //开始生产令牌
        productToken();
        //被限流的总次数
        AtomicInteger limited = new AtomicInteger(0);
        //线程池，用于模拟并发请求
        ExecutorService requestPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 1000; i++) {
            requestPool.execute(()->{
                try {
                    Thread.sleep(200);
                }catch (Exception e){
                    e.printStackTrace();
                }
                //当前线程名称
                String taskName = Thread.currentThread().getName();
                boolean intecepted = isLimit(1);
                if (intecepted){
                    //被限流的次数累计
                    System.out.println("请求："+taskName+" 被限流了,"+"限流累计次数："+limited.getAndIncrement());
                }else {
                    //正常流量业务处理
                    System.out.println("请求："+taskName+" 正常处理业务");
                }
            });
        }
    }
}
