package com.yc.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.JsonModel;
import com.yc.bean.PageBean;
import com.yc.bean.Resfood;
import com.yc.bean.ResfoodVO;
import com.yc.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resFood")
@Slf4j
@Tag(name = "菜品API",description = "菜品管理相关接口")
@RefreshScope
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Value("${res.food}")
    private String date;

    //路径参数
    @GetMapping("/findById/{fid}")
    public Map<String, Object> findById(@PathVariable Integer fid){
        DateFormat df = new SimpleDateFormat(date);
        log.info("当前时间：{}",df.format(System.currentTimeMillis()));

        Map<String ,Object> map = new HashMap<>();
        Resfood resfood = null;

        try {
            resfood = foodService.findFoodById(fid);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",0);
            map.put("msg",e.getCause());
        }
        map.put("code",1);
        map.put("obj",resfood);
        return map;
    }

    @GetMapping("findAll")
    public JsonModel findAll(){
        JsonModel jm = new JsonModel();
        List<Resfood> list = null;
        try {
            list = foodService.findAll();
        }catch (Exception e){
            jm.setCode(0);
            jm.setError(e.getCause().toString());
            e.printStackTrace();
            return jm;
        }
        jm.setCode(1);
        jm.setObj(list);
        return jm;
    }

    //利用json数据做参数
    @Operation(summary = "分页查询",description = "分页查询")
    @ApiResponse(responseCode = "200",description = "查询成功",
    content = @Content(mediaType = "application/json"
            ,schema = @Schema(implementation = PageBean.class)))
    @PostMapping("/findByPage")
    public Map<String,Object> findByPage(@RequestBody @Parameter(description = "分页信息") PageBean<Resfood> pageBean){
        Map<String,Object> map = new HashMap<>();
        Page<Resfood> page = null;//此处这个Page是dao层的组件，这种成为PO对象(持久化对象->与表结构相同),到controller层要转成VO(值对象->为了页面展示)
        page = foodService.findByPage(pageBean.getCurrent(),pageBean.getSize(),pageBean.getSortby(),pageBean.getSort());

        map.put("code",1);

        BeanUtils.copyProperties(page,pageBean);
        pageBean.calculate();
        map.put("obj",pageBean);
        return map;
    }


    @Operation(summary = "带条件的分页查询",description = "带条件的分页查询")
    @ApiResponse(responseCode = "200",description = "查询成功",
            content = @Content(mediaType = "application/json"
                    ,schema = @Schema(implementation = PageBean.class)))
    @PostMapping("/findByPageWithCondition")
    public Map<String,Object> findByPageWithCondition(@RequestBody PageBean<Resfood> pageBean){
        Map<String,Object> map = new HashMap<>();
        Page<Resfood> page = null;
        page = foodService.findFoods(pageBean.getResfood(),pageBean.getCurrent(),pageBean.getSize(),pageBean.getSortby(),pageBean.getSort());
        map.put("code",1);

        BeanUtils.copyProperties(page,pageBean);
        pageBean.calculate();
        map.put("obj",pageBean);
        return map;
    }

    @PostMapping("/addFood")
    public Map<String, Object> addFood(@ModelAttribute ResfoodVO food){
        Map<String,Object> map = new HashMap<>();
        map.put("code",1);
        map.put("obj",foodService.addResfood(food));
        return map;
    }
}
