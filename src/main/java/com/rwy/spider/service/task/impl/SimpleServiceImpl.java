package com.rwy.spider.service.task.impl;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.bean.task.Task;
import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskRuntime;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.base.BasePageProcessor;
import com.rwy.spider.service.mail.impl.MailServiceImpl;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.service.task.SchedulerService;
import com.rwy.spider.service.task.TaskRuntimeService;
import com.rwy.spider.service.task.TaskSchedulerService;
import com.rwy.spider.service.task.TaskService;
import com.rwy.spider.utils.SpringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;


/**
 * Created by Luocj on 2014/10/24.
 */
@Service("simpleService")
public class SimpleServiceImpl {

    @Resource
    private TaskService taskService;
    @Resource
    private TaskRuntimeService taskRuntimeService;
    @Resource
    private ProductService productService;
    @Resource
    private MailServiceImpl mailService;
    @Resource
    private SchedulerService schedulerService;

    private static final Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);

    public void execute(Trigger trigger) {
        Long startDate = System.currentTimeMillis();
        String triggerName = trigger.getKey().getName();
        TaskRuntime tr = taskRuntimeService.find(triggerName);
        if(null == tr){
            schedulerService.removeTrigdger(triggerName);
            return;
        }
        String type = tr.getType();
        List<Task> taskList = new ArrayList<Task>();
        if("NORMAL".equals(type)){
            logger.info("*** 景区任务开始执行，时间："+ DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" ***");
            taskList = taskService.getScrollData(-1,-1," o.runtimeId is null",null).getResultlist();
        }else if("TEMP".equals(type)){
            logger.info("*** 临时任务开始执行，时间："+ DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" ***");
            String runtimeId = tr.getId();
            taskList = taskService.getScrollData(-1,-1," o.runtimeId =?1",new Object[]{runtimeId}).getResultlist();
        }else if("SYS".equals(type)){
            logger.info("*** 系统任务开始执行，时间："+ DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" ***");
            //转移临时数据
            productService.process("{call proc_temp_to_history(?1)}",Constant.TEMP_DATA_CLEAR);
            //清理历史数据
            productService.process("{call proc_clear_history(?1)}",Constant.HISTORY_DATA_CLEAR);
            logger.info("*** 系统任务执行完毕，时间："+ DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" ***");
        }
        if(taskList.size()==0){
            return;
        }
        for(Object key : Constant.PLATFORM_MAP.keySet()){
            PlatForm pf = (PlatForm) Constant.PLATFORM_MAP.get(key);
            Object service = SpringUtils.getInstance().getBean(pf.getService());
            if(service instanceof BasePageProcessor){
                BasePageProcessor bpp = (BasePageProcessor)service;
                bpp.execute(pf,type,taskList.toArray());
            }
        }
        //调用存储过程保存分析结果
        Map map = null;
        if("NORMAL".equals(type)){
            productService.process("{call proc_analysis_result(?1)}",new Timestamp(trigger.getPreviousFireTime().getTime()));
            map = productService.getProductForEmail();
            try {
                SystemParams emailParam = Constant.SYSTEM_PARAMS.get("EMAIL");
                if(null != emailParam){
                    String[] emails = emailParam.getParamValue().split(";");
                    mailService.execute(emails,map);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if("TEMP".equals(type)){
            for(Task task : taskList){
                task.setStatus("RUNNED");
                taskService.update(task);
            }
            productService.process("{call proc_analysis_result_temp(?1)}",new Timestamp(trigger.getPreviousFireTime().getTime()));
        }
        Long endDate = System.currentTimeMillis();
        Long time = endDate - startDate;
        logger.info("本次耗时："+ time);


    }
}
