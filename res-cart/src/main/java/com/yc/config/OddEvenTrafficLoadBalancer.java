package com.yc.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Slf4j
public class OddEvenTrafficLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private final ServiceInstanceListSupplier instanceListSupplier;

    public OddEvenTrafficLoadBalancer(ServiceInstanceListSupplier instanceListSupplier) {
        this.instanceListSupplier = instanceListSupplier;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        return instanceListSupplier.get(request).next()
                .map(serviceInstances -> getInstanceResponse(serviceInstances));
    }

    //单双号
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        log.info("自定义负载");
        if (instances.isEmpty()){
            return new EmptyResponse();
        }
        int size = instances.size();
        if(size==1){
            return new DefaultResponse(instances.get(0));
        }
        int date= LocalDate.now().getDayOfMonth();//单双号
        Random random = new Random();
        int randomNum;
        if (date%2==0){//偶数天
            do{
                randomNum = random.nextInt(size);//生成0-n的随机数
            }while (randomNum % 2 != 0);//确保是偶数
            return new DefaultResponse(instances.get(randomNum));
        }else {
            do{
                randomNum = random.nextInt(size);//生成0-n的随机数
            }while (randomNum % 2 == 0);//确保是偶数
            return new DefaultResponse(instances.get(randomNum));
        }
    }
}
