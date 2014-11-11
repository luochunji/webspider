package com.rwy.spider.dto;

import com.rwy.spider.bean.quartz.QrtzTriggers;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.constant.Constant;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;

/**
 * Created by Luocj on 2014/10/28.
 */
public class TaskListDto implements Serializable {

    private TaskScheduler taskScheduler;

    private String next_fire_time;

    private String prev_fire_time;

    private String status;

    public TaskListDto(){

    }

    public TaskListDto(TaskScheduler taskScheduler, Long next_fire_time, Long prev_fire_time, String status) {
        this.taskScheduler = taskScheduler;
        this.next_fire_time = DateFormatUtils.format(next_fire_time,"yyyy-MM-dd HH:mm:ss");
        this.prev_fire_time = prev_fire_time == -1L ?"未执行":DateFormatUtils.format(prev_fire_time,"yyyy-MM-dd HH:mm:ss");
        this.status = status;
    }

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public void setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public String getNext_fire_time() {
        return next_fire_time;
    }

    public void setNext_fire_time(String next_fire_time) {
        this.next_fire_time = next_fire_time;
    }

    public String getPrev_fire_time() {
        return prev_fire_time;
    }

    public void setPrev_fire_time(String prev_fire_time) {
        this.prev_fire_time = prev_fire_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
