package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resuser")
public class Resuser implements Serializable {
    @TableId(value = "userid",type = IdType.AUTO)
    private Integer userid;
    @TableField(value = "username")
    private String username;
    @TableField(value = "pwd")
    private String pwd;
    @TableField(value = "email")
    private String email;
}
