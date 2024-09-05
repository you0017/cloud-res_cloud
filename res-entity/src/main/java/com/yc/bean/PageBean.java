package com.yc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T> {
    //前端传入的数据
    private int current=1;   //当前第几页
    private int size=5; //每页多少条
    private String sortby;  //排序列名
    private String sort;    //asc/desc


    //查询结果
    private long total; //总记录数
    private List<T> records;

    //需要计算的
    private int totalpages; //总页数
    private int pre;    //上一页
    private int next;   //下一页

    private Resfood resfood;

    //计算pre,next,totalpages
    public void calculate(){
        //其他分页数据
        //计算总页数
        long totalPages = this.total % size == 0?
                total /size : total /size + 1;
        this.totalpages = (int) totalPages;
        //上一页页号
        if (current <= 1){
            pre = 1;
        }else {
            pre = current -1;
        }
        //计算下一页的页号
        if (current == totalPages){
            next = (int) totalPages;
        }else {
            next = current +1;
        }
    }
}
