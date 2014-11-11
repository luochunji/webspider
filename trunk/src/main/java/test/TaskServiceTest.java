package test;

import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.platform.PlatFormService;
import com.rwy.spider.service.task.SchedulerService;
import com.rwy.spider.service.task.TaskExecutionService;
import com.rwy.spider.service.task.TaskSchedulerService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Luocj on 2014/10/22.
 */
public class TaskServiceTest {
    private static TaskExecutionService taskExecutionService;
    private static TaskSchedulerService taskSchedulerService;
    private static PlatFormService platFormService;
    private static SchedulerService schedulerService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            ApplicationContext cxt = new ClassPathXmlApplicationContext("spring.xml","spring-*.xml");
            taskExecutionService = (TaskExecutionService)cxt.getBean("taskExecutionService");
            taskSchedulerService = (TaskSchedulerService)cxt.getBean("taskSchedulerService");
            platFormService = (PlatFormService)cxt.getBean("platFormService");
            schedulerService = (SchedulerService)cxt.getBean("schedulerService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveTaskExecution(){

        TaskExecution te = new TaskExecution();
        te.setKeyword("灵玲马戏团");
        te.setFilterCondition("price");
        te.setFilterValue("200");
        te.setPlatform(platFormService.find(2L));

        TaskScheduler ts = new TaskScheduler();
        ts.setTaskName("灵玲马戏团限价任务");
        ts.setCreateTime(new Date());
        ts.setCreator("luocj");
        ts.setTaskStatus("1");
        ts.addTaskExecution(te);

        taskSchedulerService.save(ts);
        String cronExpression = "0 0/1 * * * ?";
        schedulerService.schedule(ts.getId().toString(),cronExpression);

    }

    @Test
    public void modifyTask(){
//        TaskScheduler ts = taskSchedulerService.find(7);
        String cronExpression = "0 25 16 * * ?";
        schedulerService.schedule("a92bf85a-49eb-44be-9b41-0d8dc579c97b","4028945d49842e240149842f31900000",cronExpression);
    }

}
