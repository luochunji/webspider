package com.rwy.spider.web.bean;

import com.rwy.spider.web.common.PageBean;

import java.io.Serializable;

/**
 * Created by Luocj on 2014/11/4.
 */
public class ProductBean extends PageBean implements Serializable {

    private String storeName;

    private String category;

    private String type;

    private String minPrice;

    private String maxPrice;

    private String validWeeks;

    private String taskExecuteId;

    private String taskId;

    private String sort;

    private String filterStore;

    private Long platFormId;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getValidWeeks() {
        return validWeeks;
    }

    public void setValidWeeks(String validWeeks) {
        this.validWeeks = validWeeks;
    }

    public String getTaskExecuteId() {
        return taskExecuteId;
    }

    public void setTaskExecuteId(String taskExecuteId) {
        this.taskExecuteId = taskExecuteId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFilterStore() {
        return filterStore;
    }

    public void setFilterStore(String filterStore) {
        this.filterStore = filterStore;
    }

    public Long getPlatFormId() {
        return platFormId;
    }

    public void setPlatFormId(Long platFormId) {
        this.platFormId = platFormId;
    }
}
