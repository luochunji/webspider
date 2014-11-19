package com.rwy.spider.web.bean;

import com.rwy.spider.constant.Constant;
import com.rwy.spider.web.common.PageBean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Luocj on 2014/10/28.
 */
public class TaskBean extends PageBean implements Serializable{

    private String id;

    private String ids;

    private String scenicId;

    private String keyword;

    private double price;

    private String conditions;

    private String runtime;

    private Map params = Constant.SYSTEM_PARAMS;

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
}
