package com.rwy.spider.web.bean;

import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.web.common.PageBean;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Luocj on 2014/10/28.
 */
public class TaskBean extends PageBean implements Serializable{

    private String id;

    private String ids;

    private String scenicId;

    private String scenicName;

    private String keyword;

    private double price;

    private String conditions;

    private String runtime;

    private Map params = Constant.SYSTEM_PARAMS;

    private String emailTitle;

    private String taskType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getScenicId() {
        return scenicId;
    }

    public void setScenicId(String scenicId) {
        this.scenicId = scenicId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getEmailTitle() {
        Object paramObj = params.get("EMAIL");
        emailTitle = "";
        if(null!=paramObj){
            SystemParams sp = (SystemParams) paramObj;
            String[] emails = sp.getParamValue().split(";");
            for(String email : emails){
                emailTitle += email+"&#10";
            }
        }
        return emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }
}
