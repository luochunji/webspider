package com.rwy.spider.web.dto;

import com.rwy.spider.annotation.ExcelExportRuleAnnotation;
import com.rwy.spider.web.bean.TaskType;

import java.util.Date;

/**
 * Created by Luocj on 2014/11/18.
 */
public class ProductDto {

    private String id;

    @ExcelExportRuleAnnotation(label = "任务属性")
    private String taskType;

    @ExcelExportRuleAnnotation(label = "景区名称")
    private String scenicName;

    @ExcelExportRuleAnnotation(label = "门票类型")
    private String type;

    @ExcelExportRuleAnnotation(label = "门票种类")
    private String category;

    @ExcelExportRuleAnnotation(label = "网络售价")
    private double sellPrice;

    @ExcelExportRuleAnnotation(label = "最低限价")
    private double lowPrice;

    @ExcelExportRuleAnnotation(label = "数据来源")
    private String platForm;

    @ExcelExportRuleAnnotation(label = "产品地址")
    private String productUrl;

    @ExcelExportRuleAnnotation(label = "店铺名称")
    private String storeName;

    private String storeUrl;

    @ExcelExportRuleAnnotation(label = "更新时间")
    private Date datetime;

    public ProductDto() {

    }

    public ProductDto(String id, String scenicName, String type, String category, double sellPrice, double lowPrice, String platForm, String productUrl, String storeName, String storeUrl,Date datetime) {
        this.id = id;
        this.scenicName = scenicName;
        this.type = type;
        this.category = category;
        this.sellPrice = sellPrice;
        this.lowPrice = lowPrice;
        this.platForm = platForm;
        this.productUrl = productUrl;
        this.storeName = storeName;
        this.storeUrl = storeUrl;
        this.datetime = datetime;
    }

    public ProductDto(String id, String scenicName, String type, String category, double sellPrice, double lowPrice, String platForm, String productUrl, String storeName, Date datetime) {
        this.id = id;
        this.scenicName = scenicName;
        this.type = type;
        this.category = category;
        this.sellPrice = sellPrice;
        this.lowPrice = lowPrice;
        this.platForm = platForm;
        this.productUrl = productUrl;
        this.storeName = storeName;
        this.datetime = datetime;
    }



    public ProductDto(String id, String taskType, String scenicName, String type, String category, double sellPrice, double lowPrice, String platForm, String productUrl, String storeName, Date datetime) {
        this.id = id;
        this.taskType = TaskType.valueOf(taskType).getName();
        this.scenicName = scenicName;
        this.type = type;
        this.category = category;
        this.sellPrice = sellPrice;
        this.lowPrice = lowPrice;
        this.platForm = platForm;
        this.productUrl = productUrl;
        this.storeName = storeName;
        this.datetime = datetime;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getPlatForm() {
        return platForm;
    }

    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
