package test;

import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.base.BasePageProcessor;
import com.rwy.spider.service.task.TaskSchedulerService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

/**
 * Created by Luocj on 2014/10/22.
 */
public class SpiderServiceTest {
    private static ApplicationContext cxt;
    private static TaskSchedulerService taskSchedulerService;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            cxt = new ClassPathXmlApplicationContext("spring.xml","spring-*.xml");
            taskSchedulerService = (TaskSchedulerService)cxt.getBean("taskSchedulerService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runSpiderTaskTest(){
        TaskScheduler ts = taskSchedulerService.find(3);
        Set<TaskExecution> tes = ts.getTes();
        for(TaskExecution te : tes){
            String serviceName = te.getPlatform().getService();
            Object service = cxt.getBean(serviceName);
            if(service instanceof BasePageProcessor){
                BasePageProcessor bpp = (BasePageProcessor)service;
                bpp.execute(te);
            }
        }
    }

}
