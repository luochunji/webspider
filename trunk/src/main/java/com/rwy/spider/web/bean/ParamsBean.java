package com.rwy.spider.web.bean;

import com.rwy.spider.web.common.PageBean;

import java.io.Serializable;

/**
 * Created by Luocj on 2014/11/14.
 */
public class ParamsBean extends PageBean implements Serializable {

    private String paramId;

    private String paramKey;

    private String paramValue;

    private String description;

    private String runtime;

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
