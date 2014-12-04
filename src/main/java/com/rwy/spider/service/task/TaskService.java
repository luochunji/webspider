package com.rwy.spider.service.task;

import com.rwy.spider.bean.task.Task;
import com.rwy.spider.service.base.DAO;
import com.rwy.spider.web.bean.TaskBean;
import com.rwy.spider.web.common.PageView;
import com.rwy.spider.web.dto.TaskDto;
import com.rwy.spider.web.dto.TaskTempDto;

import java.util.List;

/**
 * Created by Luocj on 2014/11/12.
 */
public interface TaskService extends DAO<Task> {

    /**
     * 获取常规任务列表
     * @param bean
     * @param pageView
     * @return
     */
    public PageView getTaskList(TaskBean bean, PageView pageView);

    /**
     * 获取临时任务列表
     * @param bean
     * @param pageView
     * @return
     */
    public PageView getTaskTempList(TaskBean bean, PageView pageView);


    /**
     * 获取要导出的常规任务结果集
     * @param bean
     * @param ids
     * @return
     */
    public List<TaskDto> getExportResultList(TaskBean bean, String[] ids);

    /**
     * 获取要导出的临时任务结果集
     * @param bean
     * @param ids
     * @return
     */
    public List<TaskTempDto> getExportTempResultList(TaskBean bean, String[] ids);
}
