package com.sh.openfeign;

import feign.Feign;
import feign.gson.GsonDecoder;

import java.util.List;


public class MyApp {
    public static void main(String[] args) {

        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");

        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())  // 解码器 组件  可以换
                .target(GitHub.class, "https://api.github.com");

        System.out.println(github);

        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor);
        }
    }
}