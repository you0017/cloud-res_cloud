package com.yc.controller;

import com.github.cage.Cage;
import com.github.cage.GCage;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/resSecurity")
@Slf4j
@Tag(name = "权限校验",description = "权限校验相关接口")
public class CaptchaController {

    private final Cage cage = new GCage();

    @GetMapping("/captcha")
    public String getCaptcha(HttpSession session){
        String token = cage.getTokenGenerator().next();
        log.info("验证码为：{}",token);
        session.setAttribute("captcha",token);
        //生成图片
        byte[] image = cage.draw(token);
        return Base64.getEncoder().encodeToString(image);
    }
}
