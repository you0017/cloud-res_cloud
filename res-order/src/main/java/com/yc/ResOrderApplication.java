package com.yc;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.yc.mapper")

@Slf4j

@EnableAsync
public class ResOrderApplication
{
    public static void main( String[] args )
    {
        log.info("");
        SpringApplication.run(ResOrderApplication.class, args);
    }
}
