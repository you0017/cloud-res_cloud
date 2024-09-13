package com.sh.openfeign;

import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface GitHub {
    // 传入的参数后可以拼接成url: https://api.github.com/repos/OpenFeign/feign/contributors
//  RequestLine:定义HttpMethod 和 UriTemplate. UriTemplate 中使用{} 包裹的表达式，可以通过在方法参数上使用@Param 自动注入
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);
}