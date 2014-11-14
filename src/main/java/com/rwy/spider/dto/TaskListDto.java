package com.rwy.spider.dto;

import com.rwy.spider.bean.scenic.Scenic;
import com.rwy.spider.bean.task.Task;

import java.io.Serializable;

/**
 * Created by Luocj on 2014/10/28.
 */
public class TaskListDto implements Serializable {

    private Scenic scenic;

    private Task task;

    public TaskListDto(){

    }

    public TaskListDto(Scenic scenic, Task task) {
        this.scenic = scenic;
        this.task = task;
    }

    public Scenic getScenic() {
        return scenic;
    }

    public void setScenic(Scenic scenic) {
        this.scenic = scenic;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
