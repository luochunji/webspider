package com.rwy.spider.bean.task;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Luocj on 2014/10/22.
 */
//@Entity
//@Table(name="T_TASK_SCHEDULER")
public class TaskScheduler implements Serializable{

    private String id;

    /**
     * 任务名称 用户定义
     */
    private String taskName;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 任务创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 关键字、过滤条件列表
     */
    private Set<TaskExecution> tes = new HashSet<TaskExecution>();

    /**
     * 任务生效日期
     */
    private Date startTime;

    /**
     * 任务失效时间
     */
    private Date endTime;

    /**
     * 是否发送邮件
     */
    private boolean sendMail;

    /**
     * 邮件地址
     */
    private String email;

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(length = 100)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(length = 10)
    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(length = 50)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @OneToMany(mappedBy="taskScheduler",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<TaskExecution> getTes() {
        return tes;
    }

    public void setTes(Set<TaskExecution> tes) {
        this.tes = tes;
    }

    @Temporal(TemporalType.DATE)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.DATE)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isSendMail() {
        return sendMail;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    @Column(length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addTaskExecution(TaskExecution te) {
        this.tes.add(te);
        te.setTaskScheduler(this);
    }
}
