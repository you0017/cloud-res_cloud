package com.yc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

//@Configuration
@Slf4j
public class MyLoadBalancerClientConfiguration  {
    //单双数
    @Bean
    public ReactorLoadBalancer<ServiceInstance> customLoadBalancer(ServiceInstanceListSupplier serviceInstanceListSupplier) {
        log.info("单双数");
        //
        return new OddEvenTrafficLoadBalancer(serviceInstanceListSupplier);
    }

    //   轮询
    /*@Bean
    public ReactorLoadBalancer<ServiceInstance> loadBalancer(Environment environment,
                                                             LoadBalancerClientFactory loadBalancerClientFactory) {
        log.info("轮询");
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        //根据服务实例名  name  通过ServiceInstanceListSupplier获取列表
        return new RoundRobinLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }*/

//       随机
//    @Bean
//    public ReactorLoadBalancer<ServiceInstance> loadBalancer(Environment environment,
//                                                             LoadBalancerClientFactory loadBalancerClientFactory) {
//        log.info("随机");
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new RandomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
//    }

    // 最少连接
//    @Bean
//    public ReactorLoadBalancer<ServiceInstance> loadBalancer(Environment environment,
//                                                             LoadBalancerClientFactory loadBalancerClientFactory) {
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new LeastConnectionsLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
//    }

    //  哈希表
//    @Bean
//    public ReactorLoadBalancer<ServiceInstance> loadBalancer(Environment environment,
//                                                             LoadBalancerClientFactory loadBalancerClientFactory) {
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new IpHashLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
//    }

}

