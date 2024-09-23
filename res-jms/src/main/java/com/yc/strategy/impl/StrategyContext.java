package com.yc.strategy.impl;


import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.strategy.MessageStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StrategyContext {

    private final Map<String, MessageStrategy> messageStrategies;

    public String executeStrategy(String opType, Resorder resorder, Resuser resuser) {
        String info;

        MessageStrategy messageStrategy = messageStrategies.get(opType);
        if (messageStrategy != null) {
            info = messageStrategy.transformation(resorder,resuser);
        } else {
            throw new RuntimeException("未知操作");
        }

        return info;
    }
}
