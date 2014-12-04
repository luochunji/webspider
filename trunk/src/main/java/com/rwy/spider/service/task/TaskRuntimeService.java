package com.rwy.spider.service.task;

import com.rwy.spider.bean.task.TaskRuntime;
import com.rwy.spider.service.base.DAO;

import java.util.List;
import java.util.Map;

/**
 * Created by Luocj on 2014/11/13.
 */
public interface TaskRuntimeService extends DAO<TaskRuntime> {

    /**
     * 获取所有任务
     * @return
     */
    public Map<String,TaskRuntime> getTaskRuntimeMap();

    /**
     * 获取所有常规任务
     * @return
     */
    public Map<String,TaskRuntime> getNormalTaskRuntimeMap();

    public List<TaskRuntime> getSysTaskRunTime();

    /**
     * 更新临时调度任务
     */
    public void updateRuntimeAndTriggerJob();
}
