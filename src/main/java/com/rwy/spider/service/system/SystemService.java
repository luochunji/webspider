package com.rwy.spider.service.system;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.platform.PlatFormService;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Luocj on 2014/11/7.
 */
@Service("systemService")
public class SystemService {

    @Resource
    private PlatFormService platFormService;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SystemService.class);

    @PostConstruct
    public void initParms(){

        initPlatForm();
    }

    private void initPlatForm() {
        List<PlatForm> platFormList = platFormService.getScrollData().getResultlist();
        for(PlatForm pf : platFormList){
            Constant.PLATFORM_MAP.put(pf.getId(),pf);
        }
    }
}
