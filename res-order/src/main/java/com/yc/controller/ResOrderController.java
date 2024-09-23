package com.yc.controller;

import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.context.BaseContext;
import com.yc.model.CartItem;
import com.yc.model.ResponseResult;
import com.yc.service.ResOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/resOrder")
public class ResOrderController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ResOrderService resOrderService;

    @Autowired
    private JmsMessageProducer jmsMessageProducer;

    @PostMapping("/confirmOrder")
    public ResponseResult confirmOrder(@RequestBody Resorder resorder){
        String userid = BaseContext.getCurrentId();

        if (!redisTemplate.hasKey("cart:"+userid)){
            return ResponseResult.error("购物车为空");
        }

        Map<String, CartItem> cartItemMap = (Map<String, CartItem>) redisTemplate.opsForValue().get("cart:"+userid);

        resorder.setUserid(Integer.parseInt(userid));
        //orderTime下单时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        resorder.setOrdertime(now.format(formatter));

        if (resorder.getDeliverytime()==null||resorder.getDeliverytime().equals("")){
            LocalDateTime deliverTime = now.plusHours(1);
            resorder.setDeliverytime(deliverTime.format(formatter));
        }
        resorder.setStatus(0);

        try {
            Resuser resuser = Resuser.builder().userid(Integer.parseInt(userid)).build();
            resOrderService.order(resorder, new HashSet<>(cartItemMap.values()),resuser);
        }catch (Exception e){
            return ResponseResult.error("下单失败");
        }

        jmsMessageProducer.sendMessage(resorder);
        redisTemplate.delete("cart:"+userid);
        return ResponseResult.ok("下单成功");
    }
}
