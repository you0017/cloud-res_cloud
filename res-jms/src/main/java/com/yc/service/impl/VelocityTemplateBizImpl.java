package com.yc.service.impl;


import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.service.VelocityTemplateBiz;
import com.yc.strategy.impl.StrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VelocityTemplateBizImpl implements VelocityTemplateBiz {

    @Autowired
    private StrategyContext strategyContext;


    public String genEmailContent(String opType, Resorder resorder, Resuser resuser) {

        String s = strategyContext.executeStrategy(opType, resorder,resuser);
        return s;
    }


}