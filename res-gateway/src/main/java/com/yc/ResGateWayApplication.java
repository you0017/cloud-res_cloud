package com.yc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

@Slf4j
public class ResGateWayApplication {
    public static void main(String[] args) {
        log.info("res-gateway启动成功");
        SpringApplication.run(ResGateWayApplication.class, args);
    }
}