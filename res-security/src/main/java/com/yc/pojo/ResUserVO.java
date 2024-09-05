package com.yc.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

//PO类，与数据表相关
@TableName("resuser")
@Data
public class ResUserVO {

    private Integer userid;
    private String username;
    private String password;  //UserDetail要求是password
    private String email;

    private String captcha;//验证码
}
