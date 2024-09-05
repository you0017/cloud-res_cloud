package com.yc;

import jakarta.websocket.ClientEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan(basePackages = "com.yc.mapper")
@EnableCaching
@EnableDiscoveryClient

@EnableAsync//开启异步

@Slf4j
public class ResFoodApplication
{
    public static void main( String[] args ) {
        log.info("food-service启动成功");
        SpringApplication.run(ResFoodApplication.class, args);
    }
}
