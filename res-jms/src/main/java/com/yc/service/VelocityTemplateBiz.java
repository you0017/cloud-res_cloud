package com.yc.service;


import com.yc.bean.Resorder;
import com.yc.bean.Resuser;

public interface VelocityTemplateBiz {
    public String genEmailContent(String opType, Resorder resorder, Resuser resuser);
}
