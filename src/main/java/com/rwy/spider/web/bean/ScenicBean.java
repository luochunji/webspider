package com.rwy.spider.web.bean;

import com.rwy.spider.web.common.PageBean;

import java.io.Serializable;

/**
 * Created by Luocj on 2014/11/11.
 */
public class ScenicBean extends PageBean implements Serializable {

    private String scenicName;

    private String directUrl;

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getDirectUrl() {
        return directUrl;
    }

    public void setDirectUrl(String directUrl) {
        this.directUrl = directUrl;
    }
}
