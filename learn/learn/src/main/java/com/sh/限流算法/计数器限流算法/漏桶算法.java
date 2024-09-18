package com.sh.限流算法.计数器限流算法;


import java.text.SimpleDateFormat;

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
public class 漏桶算法 {
    public static void main(String[] args) {
        /*ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(100));
        // 指定每秒放1个令牌
        RateLimiter limiter = RateLimiter.create(1);
        for (int i = 1; i < 50; i++) {
            // 请求RateLimiter, 超过permits会被阻塞
            //acquire(int permits)函数主要用于获取permits个令牌，并计算需要等待多长时间，进而挂起等待，并将该值返回
            Double acquire = null;
            if (i == 1) {
                acquire = limiter.acquire(1);
            } else if (i == 2) {
                acquire = limiter.acquire(10);
            } else if (i == 3) {
                acquire = limiter.acquire(2);
            } else if (i == 4) {
                acquire = limiter.acquire(20);
            } else {
                acquire = limiter.acquire(2);
            }
            executorService.submit(new Task("获取令牌成功，获取耗：" + acquire + " 第 " + i + " 个任务执行"));
        }*/
    }

}
class Task implements Runnable {
    String str;

    public Task(String str) {
        this.str = str;
    }

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //System.out.println(sdf.format(new Date()) + " | " + Thread.currentThread().getName() + str);
    }
}
