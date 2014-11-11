package com.rwy.spider.service.task.impl;

import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.base.BasePageProcessor;
import com.rwy.spider.service.mail.impl.MailServiceImpl;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.service.task.TaskSchedulerService;
import com.rwy.spider.utils.SpringUtils;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;


/**
 * Created by Luocj on 2014/10/24.
 */
@Service("simpleService")
public class SimpleServiceImpl {

    @Resource
    private TaskSchedulerService taskSchedulerService;
    @Resource
    private ProductService productService;
    @Resource
    private MailServiceImpl mailService;

    private static final Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);

    public void execute(Trigger trigger) {
        String triggerGroup = trigger.getKey().getGroup();
        // 这里执行定时调度业务
        System.out.println("**************任务调度开始执行："+triggerGroup +",date:"+new Date()+"*****************");

        TaskScheduler taskScheduler =  taskSchedulerService.getTaskSchedulerByTriggerGroup(triggerGroup);
        if(null != taskScheduler){
            Set<TaskExecution> tes = taskScheduler.getTes();
            for(TaskExecution te : tes){
                String serviceName = te.getPlatform().getService();
                Object service = SpringUtils.getInstance().getBean(serviceName);
                if(service instanceof BasePageProcessor){
                    BasePageProcessor bpp = (BasePageProcessor)service;
                    bpp.execute(te);
                }
            }
            Map map = productService.getAnalyseResult(taskScheduler);
            try {
                if(taskScheduler.isSendMail()){
                    mailService.execute(taskScheduler,map);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
