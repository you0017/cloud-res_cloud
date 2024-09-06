package com.yc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.mapper.ResorderMapper;
import com.yc.model.CartItem;
import com.yc.service.ResOrderService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ResOrderServiceImpl extends ServiceImpl<ResorderMapper, Resorder> implements ResOrderService {
    @Override
    public int order(Resorder resOrder, Set<CartItem> cartItems, Resuser resUser) {
        return 0;
    }
}
