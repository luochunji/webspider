package com.rwy.spider.service.task;

import com.rwy.spider.bean.task.Task;
import com.rwy.spider.dto.TaskListDto;
import com.rwy.spider.service.base.DAO;
import com.rwy.spider.web.bean.TaskBean;
import com.rwy.spider.web.common.PageView;

/**
 * Created by Luocj on 2014/11/12.
 */
public interface TaskService extends DAO<Task> {

    public PageView getTaskList(TaskBean bean, PageView pageView);
}
