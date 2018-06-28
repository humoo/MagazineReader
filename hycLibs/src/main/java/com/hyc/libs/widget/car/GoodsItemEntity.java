package com.hyc.libs.widget.car;

import java.io.Serializable;

/**
 * 商品实体类
 *
 * @author zhxumao
 *         Created on 2018/1/17 0017 11:38.
 */

public class GoodsItemEntity implements Serializable {

    private boolean isCheck;
    private String goodsPicture;
    private String goodsTitle;
    private int count;
    private double price;
    private String goodsId;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "[goodsTitle = " + goodsTitle + ",goodsId = " + goodsId + ",isCheck = " + isCheck + ",price = " + price + ",count = " + count + ",goodsPicture = " + goodsPicture + "]";
    }
}
