package com.yc.config;

import com.yc.filter.JwtFilter;
import com.yc.service.impl.ResUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//开启security
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();//密码加密算法类
    }

    //ResUserServiceImpl实现了UserDetailsService接口，重写了loadUserByUsername方法，用于从数据库中获取用户信息
    private ResUserServiceImpl resUserServiceImpl;
    @Autowired
    public void setResUserService(ResUserServiceImpl resUserServiceImpl) {
        this.resUserServiceImpl = resUserServiceImpl;
    }

    @Bean//认证服务提供器,组装组件
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());//密码加密
        provider.setUserDetailsService(resUserServiceImpl);//组装一个业务，实现了UserDetailsService接口
        return provider;
    }

    @Bean//认证管理器
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //security的配置  通过过滤器实现组件的配置
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.formLogin(AbstractHttpConfigurer::disable)//取消默认登陆页面的试用  因为是前后端分离项目

                .logout(AbstractHttpConfigurer::disable)//取消默认登出页面的试用

                .authenticationProvider(authenticationProvider())//将自己配制的PasswordEncoder放入SecurityFilterChain中

                .csrf(AbstractHttpConfigurer::disable)//禁用csrf保护，前后端分离不需要 跨域保护

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//禁用session，因为我们用了jwt

                .httpBasic(AbstractHttpConfigurer::disable)//禁用httpBasic，因为我们传输数据用的post，而且请求体是JSON

                .authorizeHttpRequests(request ->
                        request //这四个请求不需要携带名为 authorization 的请求头token，所以不需要认证
                                .requestMatchers(HttpMethod.POST,"/resSecurity/login","/resSecurity/register","/resSecurity/logout").permitAll()
                                .requestMatchers(HttpMethod.GET,"/resSecurity/captcha").permitAll()
                                .anyRequest().authenticated())//开放四个接口，一个登录，一个注册，一个退出，其他接口都需要认证
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);//将用户授权时用到JWT校验过滤器添加进SecurityFilterChain中,并放在UsernamePasswordAuthenticationFilter之前
        return httpSecurity.build();
    }
}
