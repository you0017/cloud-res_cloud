package com.yc.controller;

import com.yc.pojo.ResUser;
import com.yc.pojo.ResUserVO;
import com.yc.model.ResponseResult;
import com.yc.service.ResUserService;
import com.yc.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/resSecurity")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private ResUserService resUserService;
    @Autowired
    public void setResUserService(ResUserService resUserService) {
        this.resUserService = resUserService;
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody @Valid ResUserVO resUserVO){
        try {
            int userid = resUserService.regUser(resUserVO);
            resUserVO.setUserid(userid);
            resUserVO.setPassword("");

            return ResponseResult.ok("注册成功").setDate(resUserVO);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("注册失败");
        }
    }


    @PostMapping("/login")
    public ResponseResult login(@RequestBody @Valid ResUserVO resUserVO, HttpSession session){
        String captcha = (String) session.getAttribute("captcha");
        if(captcha == null || !captcha.equalsIgnoreCase(resUserVO.getCaptcha())){
            return ResponseResult.error("验证码错误");
        }

        //调用service的loadUserByUsername获取UserDetails
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(resUserVO.getUsername(),resUserVO.getPassword())
        );

        //进行认证
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String,String> playloadMap = new HashMap<>();
        playloadMap.put("username",userDetails.getUsername());
        playloadMap.put("userid",String.valueOf(((ResUser) userDetails).getUserid()));
        playloadMap.put("role","user");
        playloadMap.put("email",((ResUser) userDetails).getEmail());
        String jwtToken = jwtTokenUtil.encodeJWT(playloadMap);
        return ResponseResult.ok("登录成功").setDate(jwtToken);

    }

    @PostMapping("/logout")
    public ResponseResult logout(@RequestHeader("Authorization") String authorization,@RequestHeader("token") String token){
        //这里可以实现JWT黑名单机制，或者让客户删除存储的JWT
        //例如，将token添加到Redis中
        return ResponseResult.ok("退出成功");
    }

    @PostMapping("/check")
    public ResponseResult doCheck(){
        log.info("校验成功");
        return ResponseResult.ok("校验成功");
    }

}
