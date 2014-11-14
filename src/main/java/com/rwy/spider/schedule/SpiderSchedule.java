package com.rwy.spider.schedule;

import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.base.BasePageProcessor;
import com.rwy.spider.service.task.TaskSchedulerService;
import com.rwy.spider.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by Luocj on 2014/10/22.
 */
@Service("doSpiderService")
public class SpiderSchedule {
    private static final Logger logger = LoggerFactory.getLogger(SpiderSchedule.class);

    @Resource
    private TaskSchedulerService taskSchedulerService;

    public void execute(){
        List<TaskScheduler> tsList = taskSchedulerService.getScrollData().getResultlist();
        for(TaskScheduler ts : tsList){
            Set<TaskExecution> tes = ts.getTes();
            for(TaskExecution te : tes){
                String serviceName = te.getPlatform().getService();
                Object service = SpringUtils.getInstance().getBean(serviceName);
                if(service instanceof BasePageProcessor){
                    BasePageProcessor bpp = (BasePageProcessor)service;
                    logger.info("***任务编号：【"+ts.getId()+"】，任务名称：【"+ts.getTaskName()+"】开始执行***");
                    bpp.execute(te);
                }
            }
        }

    }
}
