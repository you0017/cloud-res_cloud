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
@TableName("resadmin")
public class Resadmin implements Serializable {
    @TableId(value = "userid",type = IdType.AUTO)
    private Integer raid;
    @TableField(value = "username")
    private String raname;
    @TableField(value = "pwd")
    private String rapwd;
    @TableField(value = "email")
    private String remail;
}
