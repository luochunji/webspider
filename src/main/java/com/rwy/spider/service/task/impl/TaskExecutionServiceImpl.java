package com.rwy.spider.service.task.impl;

import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.task.TaskExecutionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Luocj on 2014/10/22.
 */
@Service("taskExecutionService")
@Transactional
public class TaskExecutionServiceImpl extends DaoSupport<TaskExecution> implements TaskExecutionService {
}
