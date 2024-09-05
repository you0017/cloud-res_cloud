package com.yc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

@Slf4j
public class ResCartApplication
{
    public static void main( String[] args )
    {
        log.info("res-cart启动");
        SpringApplication.run(ResCartApplication.class, args);
    }
}
