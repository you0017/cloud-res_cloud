package com.yc.filter;

import com.yc.service.ResUserService;
import com.yc.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//1.看是否有token  2.token是否过期
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private ResUserService resUserService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader("token");//请求头中获取token

        //如果没经过if   走的else，说明没有token，代表没登录，或者过期
        if (jwtToken != null && !jwtToken.isEmpty() && jwtUtil.decodeJWTWithKey(jwtToken)!=null){
            try {
                //token可用
                Claims claims = jwtUtil.decodeJWTWithKey(jwtToken);
                String username = (String) claims.get("username");
                UserDetails userDetails = ((UserDetailsService)resUserService).loadUserByUsername(username);
                if (userDetails != null) {
                    //校验密码和权限
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            }catch (Exception e){
                log.error("token解析失败",e);
            }
        }else {
            log.info("token为空或者过期");
        }
        filterChain.doFilter(request,response);//进入下一个过滤器
    }
}
