package com.yc.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JwtTokenUtilTest {
    @Test
    public void encodeJWT(){
        String r = JwtTokenUtil.encodeJWT("itcastitcastitcastitcastitcastitcastitcastitcastitcast");
        log.info("r={}",r);
    }

    @Test
    public void decodeJWT(){
        String r = "itcastitcastitcastitcastitcastitcastitcastitcastitcast";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLmtYvor5Vqd3TnlJ_miJB0b2tlbiIsInJvbGUiOiJhZG1pbiIsIm5hbWUiOiJ5YyIsImV4cCI6MTcyNTY3NTAzNiwiaWF0IjoiZTZmMzEyMzgtY2U0Zi00M2UxLTk3N2QtMzY0MjI2ZWU4MWY4In0.p3fNljmeAj1T2hZcgonSazRU6MYOhDv1-6ViR72J2Hg";
        JwtTokenUtil.decodeJWT(token,r);
    }
}