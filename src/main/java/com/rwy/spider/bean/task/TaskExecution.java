package com.rwy.spider.bean.task;

import com.rwy.spider.bean.platform.PlatForm;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 子任务
 * Created by Luocj on 2014/10/22.
 */
//@Entity
//@Table(name="T_TASK_EXECUTION")
public class TaskExecution implements Serializable{

    private String id;

    private TaskScheduler taskScheduler;

    private String keyword;

    private PlatForm platform;

    private String filterCondition;

    private String filterValue;

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @ManyToOne(optional=false)
    @JoinColumn(name="taskId")
    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public void setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Column(length = 50)
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="platFormId")
    public PlatForm getPlatform() {
        return platform;
    }

    public void setPlatform(PlatForm platform) {
        this.platform = platform;
    }

    @Column(length = 50)
    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    @Column(length = 50)
    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
}
