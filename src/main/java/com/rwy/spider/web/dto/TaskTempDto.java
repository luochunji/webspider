package com.rwy.spider.web.dto;

import com.rwy.spider.annotation.ExcelExportRuleAnnotation;
import com.rwy.spider.web.bean.TaskStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Luocj on 2014/10/28.
 */
public class TaskTempDto implements Serializable {

    private String id;

    @ExcelExportRuleAnnotation(label = "景区名称")
    private String scenicName;

    @ExcelExportRuleAnnotation(label = "关键字")
    private String keyword;

    @ExcelExportRuleAnnotation(label = "限价")
    private double lowprice;

    @ExcelExportRuleAnnotation(label = "运行时间")
    private String runtime;

    @ExcelExportRuleAnnotation(label = "当前状态")
    private String status;

    private Date createTime;

    public TaskTempDto(){

    }

    public TaskTempDto(String id, String scenicName, String keyword, double lowprice, String runtime, String status, Date createTime) {
        this.id = id;
        this.scenicName = scenicName;
        this.keyword = keyword;
        this.lowprice = lowprice;
        this.runtime = runtime;
        this.status = TaskStatus.valueOf(status).getName();
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

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
