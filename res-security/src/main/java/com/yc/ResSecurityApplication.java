package com.yc;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.yc.mapper")
@EnableDiscoveryClient
@EnableCaching

@Slf4j
public class ResSecurityApplication {
    public static void main( String[] args )
    {
        log.info("res-security启动成功");
        SpringApplication.run( ResSecurityApplication.class, args);
    }
}
