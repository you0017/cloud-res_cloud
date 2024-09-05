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
@TableName("resorderitem")
public class Resorderitem implements Serializable {
    @TableId(value = "roiid",type = IdType.AUTO)
    private Integer roiid;
    @TableField(value = "roid")
    private Integer roid;
    @TableField(value = "fid")
    private Integer fid;
    @TableField(value = "dealprice")
    private Double dealprice;
    @TableField(value = "num")
    private Integer num;
}
