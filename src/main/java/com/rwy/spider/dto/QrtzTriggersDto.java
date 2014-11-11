package com.rwy.spider.dto;

/**
 * Created by Luocj on 2014/11/6.
 */
public class QrtzTriggersDto {

    private String triggerName;

    private String triggerGroup;

    private String schedName;

    public QrtzTriggersDto(){

    }

    public QrtzTriggersDto(String triggerName, String triggerGroup, String schedName) {
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
        this.schedName = schedName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }
}
