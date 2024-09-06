package com.yc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.api.ResFood;
import com.yc.bean.Resfood;
import com.yc.context.BaseContext;
import com.yc.model.CartItem;
import com.yc.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resCart")
public class CartController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;  //和@OpenFeignClient注解功能一样
    @Autowired
    private ResFood resFood;

    /***
     * 一次性添加多个购物项到购物车功能:使用场景:用户先将商品添加到购物车，再去登录去结算的场景，此时有token，浏览器中有购物车数据，一次性将数据传到服务器
     * (1)从token重获取用户id =》 用springmvc过滤器完成，从token重获取用户身份数据，存到request中再转入controller，我用的ThreadLocal
     * (2)从request中取出商品数据，保存到redis中，key=uid,value=list
     * (3)返回成功标志
     */

    @PutMapping("/addAllCartItems")
    public ResponseResult addAllCartItems(@RequestBody List<CartItem> cartItems){
        String userid = BaseContext.getCurrentId();

        Map<String,Object> map = new HashMap<>();
        if (redisTemplate.hasKey("cart:"+userid)){
            map = (Map<String, Object>) redisTemplate.opsForValue().get("cart:"+userid);
        }
        //循环传上来的购物车数据，如有相同则累加，没有则创建一个新的
        for (CartItem item:cartItems){
            Resfood food = item.getFood();
            //要根据商品id获取详细信息，而不能直接把价格存redis，价格会变
            food = getFoodInfo(food.getFid());

            if (map.containsKey(food.getFid()+"")){
                //此商品数量增加
                CartItem cartItem = (CartItem) map.get(food.getFid() + "");
                cartItem.setNum(cartItem.getNum()+item.getNum());
                cartItem.getSmallCount();//小计
                //map.put(food.getFid()+"",cartItem);
            } else {
                item.setFood(food);//此时food已经有详细信息了，之前前台和redis都只有id
                item.getSmallCount();
                map.put(food.getFid()+"",item);
            }
        }

        this.redisTemplate.opsForValue().set("cart:"+userid,map);
        return ResponseResult.ok("添加购物车成功").setDate(map);
    }

    private Resfood getFoodInfo(Integer fid){
        //方案一:直接使用服务ip:端口  方式固定一个
        //Map<String,Object> result = this.restTemplate.getForObject("http://localhost:8080/resFood/findById/"+fid,Map.class);
        //方案二:利用@LoadBalanced注解(要加入负载均衡依赖)开启客户端使用服务名
/*        String url = "http://res-food/resFood/findById/"+fid;
        Map<String,Object> result = this.restTemplate.getForObject(url,Map.class);*/

        Map<String, Object> result = resFood.findById(fid);
        ObjectMapper objectMapper = new ObjectMapper();
        Resfood food = objectMapper.convertValue(result.get("obj"), Resfood.class);
        return food;
    }

    /**
     * 依次添加(或减少)一个购物项，使用场景:用户已经登陆，点击加入购物车按钮，将商品添加到购物车
     * (1)从token重获取用户id =》 用springmvc过滤器完成，从token重获取用户身份数据，存到request中再转入controller，我用的ThreadLocal
     * (2)从request中取出商品数据，保存到redis中，key=uid,value=list
     * (3)返回成功标志
     */
    @PutMapping("/addCart")
    public ResponseResult addCart(@RequestParam Integer fid){
        String userid = BaseContext.getCurrentId();

        Resfood food = getFoodInfo(fid);
        Map<String,Object> cart = new HashMap<>();
        if (redisTemplate.hasKey("cart:"+userid)){
            cart = (Map<String, Object>) redisTemplate.opsForValue().get("cart:"+userid);
        }

        //一个购物车有多个购物项，用fid作为key，购物项作为value
        CartItem ci;
        if (cart.containsKey(fid+"")){
            ci = (CartItem) cart.get(fid+"");
            ci.setNum(ci.getNum()+1);
            ci.getSmallCount();
        } else {
            ci = new CartItem();
            ci.setFood(food);
            ci.setNum(1);
            ci.getSmallCount();
            cart.put(fid+"",ci);
        }
        if (ci.getNum()<=0){
            cart.remove(fid);
        }
        this.redisTemplate.opsForValue().set("cart:"+userid,cart);
        return ResponseResult.ok("添加购物车成功").setDate(cart);
    }

    /**
     * 显示购物车功能
     * (1)从token中获取用户id => 用springmvc过滤器完成，从token重获取用户身份数据，存到request中再转入controller，我用的ThreadLocal
     * (2)从redis中返回购物车数据
     */
    @GetMapping("getCartInfo")
    public ResponseResult getCartInfo(){
        String userid = BaseContext.getCurrentId();
        Map<String,Object> cart = new HashMap<>();
        if (redisTemplate.hasKey("cart:"+userid)){
            cart = (Map<String, Object>) redisTemplate.opsForValue().get("cart:"+userid);
        }
        return ResponseResult.ok("查询购物车成功").setDate(cart);
    }

    /**
     * 清空购物车功能
     */
    @DeleteMapping("/clearAll")
    public ResponseResult clearAll(){
        String userid = BaseContext.getCurrentId();
        redisTemplate.delete("cart:"+userid);
        return ResponseResult.ok("清空购物车成功");
    }
}
