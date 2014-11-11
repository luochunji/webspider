package test;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.platform.PlatFormService;
import com.rwy.spider.service.task.TaskExecutionService;
import com.rwy.spider.service.task.TaskSchedulerService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by Luocj on 2014/10/22.
 */
public class PlatFormServiceTest {
    private static PlatFormService platFormService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            ApplicationContext cxt = new ClassPathXmlApplicationContext("spring.xml","spring-*.xml");
            platFormService = (PlatFormService)cxt.getBean("platFormService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void savePlatForm(){

        PlatForm pf = new PlatForm();
        pf.setName("淘宝网");
        pf.setService("taobaoSpiderV2Service");
        pf.setStatus("1");
        pf.setVersion("1.0");
        pf.setDescription("20141030新版本");
        platFormService.save(pf);

    }

}
