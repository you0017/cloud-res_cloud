package com.yc.filter;



import com.yc.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;
    private final JwtTokenUtil jwtTool;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request
        ServerHttpRequest request = exchange.getRequest();

        //2.判断是否需要做登录拦截
        if(isExclude(request.getPath().toString())){
            //6.放行
            return chain.filter(exchange);
        }

        //3.获取token
        String token;
        List<String> headers = request.getHeaders().get("authorization");
        if (headers!=null&&!headers.isEmpty()){
            token = headers.get(0);
        } else {
            token = null;
        }

        String userId = null;
        //4.校验并解析token
        try {
            userId = jwtTool.decodeJWTWithKey(token).get("userid").toString();
        }catch (Exception e){
            //拦截，设置响应状态码401未登录/未授权
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //TODO 5.传递用户信息
        String userInfo = userId.toString();
        exchange = exchange.mutate()
                .request(builder -> builder.header("token", token))
                .build();

        //6.放行
        return chain.filter(exchange);
    }

    private boolean isExclude(String path) {
        for (String pathPattern : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(pathPattern,path)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
