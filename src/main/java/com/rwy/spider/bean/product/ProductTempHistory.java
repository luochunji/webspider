package com.rwy.spider.bean.product;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 产品信息，实体将分为含公共属性、特殊属性两种（明细）
 *
 * 此实体为对产品属性的抽象
 */
@Entity
@Table(name="T_PRODUCT_TEMP_HISTORY")
public class ProductTempHistory implements Serializable{

    /**
     * 主键
     */
    private String id;

    /**
     * 景区名称
     */
    private String scenicId;

    /**
     * 产品编号（基于平台抓取）
     */
    private String itemId;

    /**
     * 库存编号
     */
    private String skuId;

    /**
     * 任务编号
     */
    private String taskId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品链接地址
     */
    private String productUrl;

    /**
     * 产品图片链接
     */
    private String picUrl;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺链接地址
     */
    private String storeUrl;


    private Set<ProductDetail> details = new HashSet<ProductDetail>();

    /**
     * 产品价格
     */
    private double price;

    /**
     * 限价
     */
    private double low_price;

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

    /**
     * MD5校验位
     */
    private String md5Code;

    /**
     * 抓取的html源码
     */
    private String html;

    /**
     * 平台编号
     */
    private Long platFormId;

    /**
     * 数据来自的任务类型
     */
    private String taskType;

    public ProductTempHistory(){

    }

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(length = 64)
    public String getScenicId() {
        return scenicId;
    }

    public void setScenicId(String scenicId) {
        this.scenicId = scenicId;
    }

    @Column(length = 255)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(length = 32)
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    @Column(length = 64)
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Column(length = 255)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(length = 4000)
    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    @Column(length = 4000)
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Column(length = 255)
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Column(length = 4000)
    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLow_price() {
        return low_price;
    }

    public void setLow_price(double low_price) {
        this.low_price = low_price;
    }

    @Column(length = 64)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(length = 64)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(length = 4000)
    public String getValidName() {
        return validName;
    }

    public void setValidName(String validName) {
        this.validName = validName;
    }

    @Column(length = 4000)
    public String getValidDesc() {
        return validDesc;
    }

    public void setValidDesc(String validDesc) {
        this.validDesc = validDesc;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(Date orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(Date orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    @Column(length = 64)
    public String getValidWeeks() {
        return validWeeks;
    }

    public void setValidWeeks(String validWeeks) {
        this.validWeeks = validWeeks;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Column(length = 64)
    @Index(name="IDX_PRODUCT_MD5CODE")
    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    @Lob
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Long getPlatFormId() {
        return platFormId;
    }

    public void setPlatFormId(Long platFormId) {
        this.platFormId = platFormId;
    }

    @Column(length = 10)
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Transient
    public Set<ProductDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<ProductDetail> details) {
        this.details = details;
    }
}
