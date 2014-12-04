package test;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.service.platform.PlatFormService;
import com.rwy.spider.service.system.SystemParamsService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Luocj on 2014/10/22.
 */
public class SystemServiceTest {
    private static SystemParamsService systemParamsService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            ApplicationContext cxt = new ClassPathXmlApplicationContext("spring.xml","spring-*.xml");
            systemParamsService = (SystemParamsService)cxt.getBean("systemParamsService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveParams(){

        SystemParams sp = new SystemParams();
        sp.setParamKey("history_data_clear");
        sp.setParamValue("30");
        sp.setDataType("NUMBER");
        sp.setDescription("多久清理历史数据（天）");
        sp.setStatus(true);
        systemParamsService.save(sp);
    }

}
