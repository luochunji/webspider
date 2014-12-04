package com.rwy.spider.web.dto;

import com.rwy.spider.annotation.ExcelExportRuleAnnotation;
import com.rwy.spider.annotation.Merger;

/**
 * Created by Luocj on 2014/12/3.
 */
public class AgencyDto {

    @ExcelExportRuleAnnotation(label = "分销商用户名")
    @Merger(merger = true)
    private String userName;

    @ExcelExportRuleAnnotation(label = "分销商名称")
    @Merger(merger = true)
    private String AgencyName;

    @ExcelExportRuleAnnotation(label = "网店名称")
    private String storeName;

    @ExcelExportRuleAnnotation(label = "网店地址")
    private String storeUrl;

    public AgencyDto(){

    }

    public AgencyDto(String userName, String agencyName, String storeName, String storeUrl) {
        this.userName = userName;
        AgencyName = agencyName;
        this.storeName = storeName;
        this.storeUrl = storeUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAgencyName() {
        return AgencyName;
    }

    public void setAgencyName(String agencyName) {
        AgencyName = agencyName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }
}
