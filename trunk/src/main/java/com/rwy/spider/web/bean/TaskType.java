package com.rwy.spider.web.bean;

/**
 * Created by Luocj on 2014/11/26.
 */
public enum TaskType {
    NORMAL{public String getName(){return "常规";} },
    TEMP{public String getName(){return "临时";}};
    public abstract String getName();
}
