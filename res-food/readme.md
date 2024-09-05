记录一个用户访问历史(最近10次)  客户端 sessionstore  localstorage


文件上传:
    1.阿里云OSS
    2.上传文件要用异步方式  ->  线程异步池
        上传完成后，获取文件的url地址  =>  Future   .get()阻塞方法
    3.上传文件类型大小限制