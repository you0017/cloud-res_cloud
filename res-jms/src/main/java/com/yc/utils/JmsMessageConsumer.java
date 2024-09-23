package com.yc.utils;

import com.google.gson.Gson;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.mapper.ResuserMapper;
import com.yc.service.VelocityTemplateBiz;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Log4j2
public class JmsMessageConsumer {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private VelocityTemplateBiz velocityTemplateBiz;
    @Autowired
    private MailBiz mailBiz;

    @Autowired
    private ResuserMapper resuserMapper;

    @JmsListener(destination = "resOrder")//监听myQueue消息队列
    public void receiverMessage(String message){
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            log.error("线程休眠异常",e);
        }
        System.out.println("接收到消息：" + message);
        Gson gson = new Gson();
        Resorder rs = gson.fromJson(message, Resorder.class);

        Resuser resuser = resuserMapper.selectById(rs.getUserid());

        //产生要发送的邮件内容
        String context = velocityTemplateBiz.genEmailContent("order", rs,resuser);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        mailBiz.sendMail(resuser.getEmail(),"下单通知",context);

    }
}
