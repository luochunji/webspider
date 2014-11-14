package com.rwy.spider.service.task;

import com.rwy.spider.bean.task.TaskRuntime;
import com.rwy.spider.service.base.DAO;

import java.util.Map;

/**
 * Created by Luocj on 2014/11/13.
 */
public interface TaskRuntimeService extends DAO<TaskRuntime> {

    public Map<String,TaskRuntime> getTaskRuntimeMap();

    public Map<String,TaskRuntime> getNormalTaskRuntimeMap();
}
