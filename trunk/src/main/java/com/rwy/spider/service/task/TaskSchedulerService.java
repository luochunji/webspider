package com.rwy.spider.service.task;

import com.rwy.spider.bean.quartz.QrtzTriggers;
import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.dto.QrtzTriggersDto;
import com.rwy.spider.dto.TaskListDto;
import com.rwy.spider.service.base.DAO;
import com.rwy.spider.web.common.PageView;

import java.util.List;
import java.util.Map;

/**
 * Created by Luocj on 2014/10/22.
 */
public interface TaskSchedulerService extends DAO<TaskScheduler> {


    /**
     * 获取任务列表
     * @param pageView
     * @return
     */
    public PageView getTaskList(PageView pageView);

    /**
     * 根据调度组获取任务
     * @param triggerGroup 调度组
     * @return
     */
    public TaskScheduler getTaskSchedulerByTriggerGroup(String triggerGroup);

    /**
     * 获取任务明细
     * @param taskId 任务编号
     * @return
     */
    public List<TaskExecution> getTaskDetail(String taskId);

    /**
     * 更新任务状态
     * @param taskScheduler 任务对象
     * @param operate       操作类别
     * @return
     */
    public boolean updateTask(TaskScheduler taskScheduler,String operate);

    public List<QrtzTriggersDto> getQrtzTriggersByGroup(String triggerGroup);

}
