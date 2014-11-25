package com.rwy.spider.web.bean;

import com.rwy.spider.web.common.PageBean;

import java.io.Serializable;

/**
 * Created by Luocj on 2014/11/4.
 */
public class ProductBean extends PageBean implements Serializable {

    private String ids;

    private String storeName;

    private String category;

    private String type;

    private String minPrice;

    private String maxPrice;

    private String validWeeks;

    private String taskExecuteId;

    private String taskId;

    private String sort;

    private String filterStore ="ourStore";

    private Long platFormId;

    private String keyword;

    private String clazz;

    private String startDate;

    private String endDate;

    private String url;


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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
