package com.yc.aspect;

import com.yc.bean.Resfood;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
@Order(1)
@Slf4j
public class DetailCountAspect {

    //<String简直类型,Object类型>
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Pointcut("execution(* com.yc.controller.FoodController.findById(..))")
    private void deposit() {}

    @AfterReturning(value = "deposit()",returning = "r")
    public void afterReturning(JoinPoint joinPoint, Object r) {
        Integer fid = (Integer) joinPoint.getArgs()[0];
        log.info("统计菜品访问量");
        //获取访问量
        long count = redisTemplate.opsForValue().increment("food_count:"+fid);

        Map<String, Object> map = (Map<String, Object>) r;
        if (map.get("code")!=null && map.get("code").equals(1)){
            Resfood rf = (Resfood) map.get("obj");
            rf.setDetailCount(count);
        }
    }

    //@Around("deposit()")
    public Object around(ProceedingJoinPoint pjp) {
        Integer fid = (Integer) pjp.getArgs()[0];
        log.info("统计菜品访问量");
        //获取访问量
        long count = redisTemplate.opsForValue().increment("food_count:"+fid);

        Object r = null;
        try {
            r = pjp.proceed();//开始运行,r是此方法的返回值
            Map<String, Object> map = (Map<String, Object>) r;
            if (map.get("code")!=null && map.get("code").equals(1)){
                Resfood rf = (Resfood) map.get("obj");
                rf.setDetailCount(count);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return r;
    }
}
