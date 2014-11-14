package com.rwy.spider.dto;

import java.io.Serializable;

/**
 * Created by Luocj on 2014/11/13.
 */
public class ResultListDto implements Serializable {

    private String scenicName;

    private String category;

    private String type;

    private String sellPrice;

    private String lowPrice;

    private String platForm;

    private String prouctUrl;

    private String storeName;

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
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

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getPlatForm() {
        return platForm;
    }

    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }

    public String getProuctUrl() {
        return prouctUrl;
    }

    public void setProuctUrl(String prouctUrl) {
        this.prouctUrl = prouctUrl;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
