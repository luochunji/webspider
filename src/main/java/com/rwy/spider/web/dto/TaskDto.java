package com.rwy.spider.web.dto;

import com.rwy.spider.annotation.ExcelExportRuleAnnotation;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Luocj on 2014/10/28.
 */
public class TaskDto implements Serializable {

    private String id;

    @ExcelExportRuleAnnotation(label = "景区名称")
    private String scenicName;

    @ExcelExportRuleAnnotation(label = "关键字")
    private String keyword;

    @ExcelExportRuleAnnotation(label = "限价")
    private double lowprice;

    private Date createTime;

    public TaskDto(){

    }

    public TaskDto(String id, String scenicName, String keyword, double lowprice, Date createTime) {
        this.id = id;
        this.scenicName = scenicName;
        this.keyword = keyword;
        this.lowprice = lowprice;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public double getLowprice() {
        return lowprice;
    }

    public void setLowprice(double lowprice) {
        this.lowprice = lowprice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
