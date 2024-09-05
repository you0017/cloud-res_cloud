package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resfood")
public class ResfoodVO implements Serializable {
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

    @JsonIgnore  //return的json响应数据不需要返回
    private MultipartFile file;
}
