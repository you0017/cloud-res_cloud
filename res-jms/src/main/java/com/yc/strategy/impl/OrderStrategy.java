package com.yc.strategy.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.mapper.ResOrderItemMapper;
import com.yc.strategy.MessageStrategy;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Component(value = "order")
public class OrderStrategy implements MessageStrategy {
    @Autowired
    protected VelocityContext context;
    @Autowired
    @Qualifier("withdrawTemplate")
    private Template withdrawTemplate;
    @Autowired
    @Qualifier("orderTemplate")
    private Template orderTemplate;
    @Autowired
    @Qualifier("transferTemplate")
    private Template transferTemplate;
    @Autowired
    @Qualifier("fullDf")
    private DateFormat fullDf;
    @Autowired
    @Qualifier("partDf")
    private DateFormat partDf;

    @Autowired
    private ResOrderItemMapper resOrderItemMapper;
    @Override
    public String transformation( Resorder resorder, Resuser resuser) {
        Date d = new Date();
        //模板上下文，用于存占位符的值
        context.put("accountid", resorder.getUserid());
        context.put("email", resuser.getEmail());
        context.put("subject","下单操作通知");
        context.put("optime",fullDf.format(d));
        context.put("currentDate",partDf.format(d));

        LambdaQueryWrapper<Resorderitem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Resorderitem::getRoid,resorder.getRoid());
        List<Resorderitem> resorderitems = resOrderItemMapper.selectList(queryWrapper);
        int sum = 0;
        for (Resorderitem resorderitem : resorderitems) {
            sum+=resorderitem.getNum()*resorderitem.getDealprice();
        }

        context.put("money",sum);

        //合并模板和容器
        //Template template = velocityEngine.getTemplate("vms/deposit.vm","utf-8");

        try(StringWriter writer = new StringWriter()){
            //template.merge(context,writer);//合并内容，替换占位符
            orderTemplate.merge(context,writer);//合并内容，替换占位符
            return writer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
