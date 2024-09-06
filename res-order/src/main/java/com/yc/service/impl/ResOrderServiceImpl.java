package com.yc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.mapper.ResOrderItemMapper;
import com.yc.mapper.ResOrderMapper;
import com.yc.model.CartItem;
import com.yc.service.ResOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,
        timeout = 2000,
        readOnly = false,
        rollbackFor = RuntimeException.class
)
public class ResOrderServiceImpl extends ServiceImpl<ResOrderMapper, Resorder> implements ResOrderService {
    @Autowired
    private ResOrderMapper resOrderMapper;
    @Autowired
    private ResOrderItemMapper resOrderItemMapper;

    @Override
    public int order(Resorder resOrder, Set<CartItem> cartItems, Resuser resUser) {
        resOrder.setUserid(resUser.getUserid()); //下单人编号

        this.resOrderMapper.insert(resOrder);
        for (CartItem item : cartItems){
            Resorderitem roi = new Resorderitem();
            roi.setFid(item.getFood().getFid());
            roi.setNum(item.getNum());
            roi.setRoid(resOrder.getRoid());
            roi.setDealprice(item.getSmallCount());
            this.resOrderItemMapper.insert(roi);
        }
        return 1;
    }
}
