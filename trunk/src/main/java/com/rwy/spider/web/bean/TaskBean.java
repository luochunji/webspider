package com.rwy.spider.web.bean;

import java.io.Serializable;

/**
 * Created by Luocj on 2014/10/28.
 */
public class TaskBean implements Serializable{

    private String taskName;

    private String startTime;

    private String endTime;

    private String everyWhat;

    private String week;

    private String dayOfMonth;

    private String dayOfWeek;

    private String hour;

    private String minute;

    private String second;

    private boolean sendMail;

    private String email;

    private String jsonArray;

    private String quartzArray;

    public String getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(String jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEveryWhat() {
        return everyWhat;
    }

    public void setEveryWhat(String everyWhat) {
        this.everyWhat = everyWhat;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public boolean isSendMail() {
        return sendMail;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuartzArray() {
        return quartzArray;
    }

    public void setQuartzArray(String quartzArray) {
        this.quartzArray = quartzArray;
    }
}
