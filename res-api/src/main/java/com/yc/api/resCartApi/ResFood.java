package com.yc.api.resCartApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;
@FeignClient("res-food")
public interface ResFood {

    @GetMapping("/resFood/findById/{fid}")
    public Map<String, Object> findById(@PathVariable Integer fid);
}
