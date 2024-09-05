加商品到购物车的两种情况:
1.没有登录下，用户将购物数据保存在浏览器的sessionStorage或localstorage中,=>用前端js代码完成
2. 登录下，用户将购物数据保存在redis中，     购物车中商品个数为1或多个.   *******


1.添加购物车功能完成分析:  
一次性添加多个购物项到购物车功能:  使用场景: 用户先将商品添加到购物车, 然后再登录去结算的场景，此时有token,浏览器中有购物车数据，一次性将此数据传到服务器.
(1)从token中获取用户id  => 用springmvc过滤器完成,从token中获取用户身份数据，存到request中转入controller
(2)从request中取出 用户数据，再取出商品数据，保存到 redis中. 
     redis中采用  userid -> Cart  键值对的形式保存购物车数据.
(3)返回成功标志信息

一次添加(或减少)一个购物项，使用场景: 用户已经登录, 点击加入购物车+ -  按钮，将商品添加到购物车.
(1)从token中获取用户id  => 用springmvc 拦截器完成,从token中获取用户身份数据， 身份没有过期,   存到request中转入controller
(2)在controller从request中取出 用户数据,再取出商品数据，保存到 redis中.
     redis中采用  "cart:userid" -> Cart  键值对Map<String,CartItem>的形式保存购物车数据.
(3)返回整个购物车数据到前端. 

2.显示购物车功能分析:
(1)从token中获取用户id  => 用springmvc过滤器完成,从token中获取用户身份数据，存到request中转入controller
(2)从request中取出 用户数据,再取出userid, 从redis中取出购物车数据,返回给前端.

3. 清空购物车功能分析:
(1)从token中获取用户id  => 用springmvc过滤器完成,从token中获取用户身份数据，存到request中转入controller
(2)从request中取出 用户数据,再取出userid, 从redis中删除购物车. 


