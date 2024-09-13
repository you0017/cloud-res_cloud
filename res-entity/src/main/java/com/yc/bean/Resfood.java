package com.yc.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resfood")
@Builder
public class Resfood implements Serializable {
    @TableId(value = "fid",type = IdType.AUTO)
    private Integer fid;
    @TableField(value = "fname")
    private String fname;
    @TableField(value = "normprice")
    private Double normprice;
    @TableField(value = "realprice")
    private Double realprice;
    @TableField(value = "detail")
    private String detail;
    @TableField(value = "fphoto")
    private String fphoto;

    //查看详情数，redis提供
    @TableField(exist = false)
    private Long detailCount;

    @TableField(exist = false)
    private Integer praise;
}
