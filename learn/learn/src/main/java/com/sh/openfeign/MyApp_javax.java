package com.sh.openfeign;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.jaxrs.JAXRSContract;
import feign.okhttp.OkHttpClient;

import java.util.List;
 
public class MyApp_javax {
    public static void main(String[] args) {
         GitHub2_javax github = Feign.builder()

                .decoder(new GsonDecoder())
                 .client(new OkHttpClient())

                .options(  new Request.Options(1000,3500))  //options方法指定连接超时时长及响应超时时长

                // retryer方法主要是指定重试策略
                .retryer(  new Retryer.Default(5000,5000,3))

                // Contract 指明是哪种注解规范
                .contract(new JAXRSContract())

                // 为构造器配置本地的代理接口，和远程的根目录。代理接口类的每一个接口方法前@RequestLine 声明的值，最终都会加上这个根目录。
                .target(GitHub2_javax.class, "https://api.github.com");  
 
 
        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor);
        }
    }
}