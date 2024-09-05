package com.yc.service.impl;

import com.aliyun.oss.internal.OSSUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.bean.Resfood;
import com.yc.bean.ResfoodVO;
import com.yc.mapper.FoodMapper;
import com.yc.service.FileService;
import com.yc.service.FoodService;
import com.yc.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Resfood> implements FoodService {

    @Autowired
    private FileService fileService;

    @Autowired
    private FoodMapper foodMapper;
    @Value("${spring.cache.cacheName}")
    private String cacheName;//缓存名称

    @Cacheable(cacheNames = "#{@foodServiceImpl.cacheName}", key = "'all'")
    @Override
    public List<Resfood> findAll() {
        return this.query().orderByDesc("fid").list();
    }

    @Cacheable(cacheNames = "#{@foodServiceImpl.cacheName}", key = "#fid")
    @Override
    public Resfood findFoodById(Integer fid) {
        return foodMapper.selectById(fid);
    }

    private Page<Resfood> findFoods(QueryWrapper qw, int pageNo, int pageSize, String sortBy, String sort){
        if ("asc".equalsIgnoreCase(sort)){
            qw.orderByAsc(sortBy);
        }else {
            qw.orderByDesc(sortBy);
        }
        Page<Resfood> page = new Page<>(pageNo,pageSize);
        //执行分页查询
        Page<Resfood> resfoodPage = foodMapper.selectPage(page, qw);
        log.info("总记录数 = "+resfoodPage.getTotal());
        log.info("总页数 = " + resfoodPage.getPages());
        log.info("当前页 = " + resfoodPage.getCurrent());
        return resfoodPage;
    }


    @Override
    public Page<Resfood> findFoods(Resfood food, int pageNo, int pageSize, String sortBy, String sort) {
        QueryWrapper wrapper = new QueryWrapper();
        if (food!=null){
            if (food.getFname()!=null && !"".equals(food.getFname())){
                wrapper.like("fname",food.getFname());
            }
            if (food.getDetail()!=null && !"".equals(food.getDetail())){
                wrapper.like("detail",food.getDetail());
            }
        }
        return findFoods(wrapper, pageNo, pageSize, sortBy, sort);
    }


    @Cacheable(cacheNames = "#{@foodServiceImpl.cacheName}", key = "#pageNo+'-'+#pageSize+'-'+#sortBy+'-'+#sort")
    @Override
    public Page<Resfood> findByPage(int pageNo, int pageSize, String sortBy, String sort) {
        return findFoods(new QueryWrapper<>(),pageNo,pageSize,sortBy,sort);
    }

    @CacheEvict(cacheNames = "#{@foodServiceImpl.cacheName}", allEntries = true)
    @Override
    @Transactional(
            propagation = Propagation.SUPPORTS, //支持事务环境下运行
            isolation = Isolation.DEFAULT,//隔离级别与数据库一直
            timeout = 2000,
            readOnly = false,
            rollbackFor = Exception.class
    )
    public ResfoodVO addResfood(ResfoodVO food) {
        String upload = "";
        try {
             upload = fileService.upload(food.getFile()).get();
        }catch (Exception e){
            log.error("上传图片失败",e);
        }
        Resfood resfood = Resfood.builder()
                .fname(food.getFname())
                .normprice(food.getNormprice())
                .realprice(food.getRealprice())
                .detail(food.getDetail())
                .fphoto(upload)
                .build();
        foodMapper.insert(resfood);
        BeanUtils.copyProperties(resfood,food);
        //food.setFile(null);
        return food;
    }
}
