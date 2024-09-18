package com.sh.职责链;

// 定义一个处理器抽象类
abstract class Handler {
    protected Handler nextHandler;
    
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
    
    public abstract void handleRequest(String request);
}

// 具体的处理器 - 权限验证
class AuthHandler extends Handler {
    public void handleRequest(String request) {
        if ("auth".equals(request)) {
            System.out.println("用户权限验证通过");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}

// 具体的处理器 - 身份验证
class IdentityHandler extends Handler {
    public void handleRequest(String request) {
        if ("identity".equals(request)) {
            System.out.println("用户身份验证通过");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}

// 测试
public class Main {
    public static void main(String[] args) {
        Handler authHandler = new AuthHandler();
        Handler identityHandler = new IdentityHandler();
        
        authHandler.setNextHandler(identityHandler);
        
        authHandler.handleRequest("identity");  // 输出：用户身份验证通过
    }
}
