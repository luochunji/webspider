package com.rwy.spider.service.system;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.platform.PlatFormService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Luocj on 2014/11/7.
 */
@Service("systemInitService")
public class SystemInitService {

    @Resource
    private PlatFormService platFormService;

    @Resource
    private SystemParamsService systemParamsService;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SystemInitService.class);

    @PostConstruct
    public void initParms(){
        initPlatForm();
        initSystemParams();
    }

    private void initSystemParams() {
        List<SystemParams> systemParamsList = systemParamsService.getScrollData().getResultlist();
        for(SystemParams params : systemParamsList){
            Constant.SYSTEM_PARAMS.put(params.getParamKey(),params);
        }
    }

    private void initPlatForm() {
        List<PlatForm> platFormList = platFormService.getScrollData().getResultlist();
        for(PlatForm pf : platFormList){
            Constant.PLATFORM_MAP.put(pf.getId(),pf);
        }
    }
}
