package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("resorder")
public class Resorder implements Serializable {
    @TableId(value = "roid",type = IdType.AUTO)
    private Integer roid;
    @TableField(value = "userid")
    private Integer userid;
    @TableField(value = "address")
    private String address;
    @TableField(value = "tel")
    private String tel;
    @TableField(value = "ordertime")
    private String ordertime;
    @TableField(value = "deliverytime")
    private String deliverytime;
    @TableField(value = "ps")
    private String ps;
    @TableField(value = "status")
    private Integer status;
}
