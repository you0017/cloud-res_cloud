package com.yc.api;


import com.yc.model.CartItem;
import com.yc.model.ResponseResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

public interface ResCartApi {

    /***
     * 一次性添加多个购物项到购物车功能:使用场景:用户先将商品添加到购物车，再去登录去结算的场景，此时有token，浏览器中有购物车数据，一次性将数据传到服务器
     * (1)从token重获取用户id =》 用springmvc过滤器完成，从token重获取用户身份数据，存到request中再转入controller，我用的ThreadLocal
     * (2)从request中取出商品数据，保存到redis中，key=uid,value=list
     * (3)返回成功标志
     */
    @PutMapping("/addAllCartItems")
    public ResponseResult addAllCartItems(@RequestBody List<CartItem> cartItems);


    /**
     * 依次添加(或减少)一个购物项，使用场景:用户已经登陆，点击加入购物车按钮，将商品添加到购物车
     * (1)从token重获取用户id =》 用springmvc过滤器完成，从token重获取用户身份数据，存到request中再转入controller，我用的ThreadLocal
     * (2)从request中取出商品数据，保存到redis中，key=uid,value=list
     * (3)返回成功标志
     */
    @PutMapping("/addCart")
    public ResponseResult addCart(@RequestParam Integer fid);

    /**
     * 显示购物车功能
     * (1)从token中获取用户id => 用springmvc过滤器完成，从token重获取用户身份数据，存到request中再转入controller，我用的ThreadLocal
     * (2)从redis中返回购物车数据
     */
    @GetMapping("getCartInfo")
    public ResponseResult getCartInfo();

    /**
     * 清空购物车功能
     */
    @DeleteMapping("/clearAll")
    public ResponseResult clearAll();
}
