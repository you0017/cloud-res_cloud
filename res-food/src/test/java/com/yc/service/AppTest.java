package com.yc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.ResFoodApplication;
import com.yc.bean.Resfood;
import com.yc.bean.ResfoodVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResFoodApplication.class)
@Slf4j
public class AppTest {

    @Autowired
    private FoodService foodService;

    @Test
    public void findAll(){
        List<Resfood> list = foodService.findAll();
        log.info("food list:{}",list);
        Assert.assertNotNull(list);
        List<Resfood> list2 = foodService.findAll();
        Assert.assertNotNull(list2);
        log.info("food list2:{}",list2);
    }


    @Test
    public void findById(){
        Resfood food = foodService.findFoodById(1);
        log.info("food:{}",food);
        Assert.assertNotNull(food);
        Resfood food2 = foodService.findFoodById(1);
        Assert.assertNotNull(food2);
        log.info("food2:{}",food2);
    }

    @Test
    public void findFoods(){
        Resfood food = new Resfood();
        food.setFname("肉");
        food.setDetail("吃");
        Page<Resfood> page = foodService.findFoods(food, 1, 5, "fid", "desc");
        log.info(page.toString());
        Assert.assertNotNull(page);

        Page<Resfood> page2 = foodService.findFoods(food, 1, 5, "fid", "desc");
        Assert.assertNotNull(page2);
        log.info(page2.toString());
    }


    @Test
    public void findByPage(){
        Page<Resfood> page = foodService.findByPage(1, 5, "fid", "desc");
        log.info(page.toString());
        Assert.assertNotNull(page);
        Page<Resfood> page2 = foodService.findByPage(1, 5, "fid", "desc");
        Assert.assertNotNull(page2);
        log.info(page2.toString());
    }


    @Test
    public void addResfood(){
        ResfoodVO food = new ResfoodVO();
        food.setFname("肉蛋汤");
        food.setDetail("好吃");
        food.setRealprice(20.0);
        food.setNormprice(25.0);
        food.setFphoto("1.jpg");

        ResfoodVO resfoodVO = foodService.addResfood(food);
        Assert.assertNotNull(resfoodVO.getFid());
        log.info("food id:{}",resfoodVO.getFid());
    }
}
