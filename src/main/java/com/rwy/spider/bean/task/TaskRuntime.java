package com.rwy.spider.bean.task;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Luocj on 2014/11/13.
 */
@Entity
@Table(name = "T_TASK_RUNTIME")
public class TaskRuntime implements Serializable {

    private String id;

    /**
     * 对应的任务编号
     */
    private String taskId;

    /**
     * 对应的任务类型（NORMAL-常规任务;TEMP-临时任务）
     */
    private String type;

    /**
     * 运行时间
     */
    private String runtime;

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(length = 64)
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Column(length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(length = 32)
    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }
}
