package com.yc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yc.pojo.ResUser;
import com.yc.pojo.ResUserVO;

public interface ResUserService extends IService<ResUser> {
    //ResUserVO是要返回给前端的数据
    public int regUser(ResUserVO resUserVO);
}
