package com.yc.model;

import com.yc.bean.Resfood;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Resfood food;
    private int num;
    private double smallCount;


    public double getSmallCount() {
        if (food != null){
            smallCount = this.food.getRealprice() * this.num;
        }
        return smallCount;
    }

    public void setSmallCount(double smallCount) {
        this.smallCount = smallCount;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "food=" + food +
                ", num=" + num +
                ", smallCount=" + smallCount +
                '}';
    }

    public Resfood getFood() {
        return food;
    }

    public void setFood(Resfood food) {
        this.food = food;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
