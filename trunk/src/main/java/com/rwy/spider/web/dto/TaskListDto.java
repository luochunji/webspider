package com.rwy.spider.web.dto;

import com.rwy.spider.bean.scenic.Scenic;
import com.rwy.spider.bean.task.Task;
import com.rwy.spider.bean.task.TaskRuntime;

import java.io.Serializable;

/**
 * Created by Luocj on 2014/10/28.
 */
public class TaskListDto implements Serializable {

    private Scenic scenic;

    private Task task;

    private TaskRuntime taskRuntime;

    public TaskListDto(){

    }

    public TaskListDto(Scenic scenic, Task task) {
        this.scenic = scenic;
        this.task = task;
    }

    public TaskListDto(Scenic scenic, Task task, TaskRuntime taskRuntime) {
        this.scenic = scenic;
        this.task = task;
        this.taskRuntime = taskRuntime;
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

    public TaskRuntime getTaskRuntime() {
        return taskRuntime;
    }

    public void setTaskRuntime(TaskRuntime taskRuntime) {
        this.taskRuntime = taskRuntime;
    }
}
