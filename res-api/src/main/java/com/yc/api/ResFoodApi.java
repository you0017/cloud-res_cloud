package com.yc.api;

import com.yc.bean.JsonModel;
import com.yc.bean.PageBean;
import com.yc.bean.Resfood;
import com.yc.bean.ResfoodVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@FeignClient("res-food")
public interface ResFoodApi {

    @GetMapping("/resFood/findById/{fid}")
    public Map<String, Object> findById(@PathVariable Integer fid);

    @GetMapping("/resFood/findAll")
    public JsonModel findAll(@RequestHeader String token);

    @PostMapping("/resFood/findByPage")
    public Map<String,Object> findByPage(@RequestBody PageBean<Resfood> pageBean);


    @PostMapping("/resFood/findByPageWithCondition")
    public Map<String,Object> findByPageWithCondition(@RequestBody PageBean<Resfood> pageBean);

    @PostMapping("/resFood/addFood")
    public Map<String, Object> addFood(@ModelAttribute ResfoodVO food);
}
