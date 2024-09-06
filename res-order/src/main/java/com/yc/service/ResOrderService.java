package com.yc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.model.CartItem;

import java.util.Set;

public interface ResOrderService extends IService<Resorder> {

    /**
     * 添加订单
     */
    public int order(Resorder resOrder, Set<CartItem> cartItems, Resuser resUser);
}
