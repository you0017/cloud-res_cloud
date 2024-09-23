package com.yc.strategy;


import com.yc.bean.Resorder;
import com.yc.bean.Resuser;

public interface MessageStrategy {

    public String transformation(Resorder resorder, Resuser resuser);
}
