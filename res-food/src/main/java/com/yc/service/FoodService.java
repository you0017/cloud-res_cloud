package com.yc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yc.bean.Resfood;
import com.yc.bean.ResfoodVO;

import java.util.List;

public interface FoodService extends IService<Resfood> {

    //查询所有
    public List<Resfood> findAll();

    public Resfood findFoodById(Integer fid);

    public Page<Resfood> findFoods(Resfood food, int pageNo, int pageSize, String sortBy, String sort);

    //mybatisplus自带的分页组件
    public Page<Resfood> findByPage(int pageNo, int pageSize, String sortBy, String sort);


    //上架菜品
    public ResfoodVO addResfood(ResfoodVO food);
}
