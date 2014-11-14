package com.rwy.spider.service.task.impl;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.bean.task.Task;
import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskRuntime;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.base.BasePageProcessor;
import com.rwy.spider.service.mail.impl.MailServiceImpl;
import com.rwy.spider.service.product.ProductService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

    private static final Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);

    public void execute(Trigger trigger) {
        String triggerName = trigger.getKey().getName();

        TaskRuntime tr = taskRuntimeService.find(triggerName);
        String type = tr.getType();
        if("NORMAL".equals(type)){
            logger.info("*** 景区任务开始执行，时间："+ DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" ***");
            Long startDate = System.currentTimeMillis();
            List<Task> taskList = taskService.getScrollData().getResultlist();
            for(Object key : Constant.PLATFORM_MAP.keySet()){
                PlatForm pf = (PlatForm) Constant.PLATFORM_MAP.get(key);
                Object service = SpringUtils.getInstance().getBean(pf.getService());
                if(service instanceof BasePageProcessor){
                    BasePageProcessor bpp = (BasePageProcessor)service;
                    bpp.execute(pf,taskList.toArray());
                }
            }
            Long endDate = System.currentTimeMillis();
            Long time = endDate - startDate;
            logger.info("本次耗时："+ time);
        }else if("TEMP".equals(type)){

        }
//        Map map = productService.getAnalyseResult();
//        try {
//            if(taskScheduler.isSendMail()){
//                mailService.execute(taskScheduler,map);
//            }
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
