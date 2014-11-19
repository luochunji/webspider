package com.rwy.spider.web.bean;

import com.rwy.spider.web.common.PageBean;

import java.io.Serializable;

/**
 * Created by Luocj on 2014/11/11.
 */
public class AgencyBean extends PageBean implements Serializable {

//    private String agencyId;

    private Long platFormId;

    private String storeName;

    private String status;

    private String userName;

    private String name;

    private String storeUrl;

    private String storeInfo;

    private String condition;

    private String keyword;

    public Long getPlatFormId() {
        return platFormId;
    }

    public void setPlatFormId(Long platFormId) {
        this.platFormId = platFormId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(String storeInfo) {
        this.storeInfo = storeInfo;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
