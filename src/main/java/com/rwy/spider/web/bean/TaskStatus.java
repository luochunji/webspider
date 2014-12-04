package com.rwy.spider.web.bean;

/**
 * Created by Luocj on 2014/11/26.
 */
public enum TaskStatus {
    RUNNED{public String getName(){return "已运行";} },
    WATTING{public String getName(){return "等待中";}};
    public abstract String getName();
}
