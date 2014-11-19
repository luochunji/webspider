package com.rwy.spider.bean.product;

import java.util.Date;

/**
 * Created by Luocj on 2014/10/31.
 */
public class ProductDetail {

    /**
     * 库存量编号
     */
    private String skuId;

    /**
     * 产品价格
     */
    private double price;

    /**
     * 门票种类：成人票、儿童票
     */
    private String category;

    /**
     * 门票类型：实体票、电子票
     */
    private String type;

    /**
     * 有效期
     */
    private String validName;

    /**
     * 入园限制
     */
    private String validDesc;

    /**
     * 产品生效时间
     */
    private Date orderStartDate;

    /**
     * 产品失效时间
     */
    private Date orderEndDate;

    /**
     * 每周消费时间
     */
    private String validWeeks;

    /**
     * 数据抓取时间（增量更新，只保存最新的）
     */
    private Date timeStamp = new Date();

    public ProductDetail(){

    }

    public ProductDetail(String skuId,double price, String category, String type, String validName, String validDesc, Date orderStartDate, Date orderEndDate, String validWeeks) {
        this.skuId = skuId;
        this.price = price;
        this.category = category;
        this.type = type;
        this.validName = validName;
        this.validDesc = validDesc;
        this.orderStartDate = orderStartDate;
        this.orderEndDate = orderEndDate;
        this.validWeeks = validWeeks;
    }

    public ProductDetail(double price) {
        this.price = price;
    }

    public ProductDetail(String skuId,double price, String category, String type) {
        this.skuId = skuId;
        this.price = price;
        this.category = category;
        this.type = type;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidName() {
        return validName;
    }

    public void setValidName(String validName) {
        this.validName = validName;
    }

    public String getValidDesc() {
        return validDesc;
    }

    public void setValidDesc(String validDesc) {
        this.validDesc = validDesc;
    }

    public Date getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(Date orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    public Date getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(Date orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    public String getValidWeeks() {
        return validWeeks;
    }

    public void setValidWeeks(String validWeeks) {
        this.validWeeks = validWeeks;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
