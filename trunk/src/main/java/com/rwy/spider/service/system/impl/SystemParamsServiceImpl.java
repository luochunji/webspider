package com.rwy.spider.service.system.impl;

import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.system.SystemInitService;
import com.rwy.spider.service.system.SystemParamsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Luocj on 2014/11/14.
 */
@Service("systemParamsService")
@Transactional
public class SystemParamsServiceImpl extends DaoSupport<SystemParams> implements SystemParamsService {

    @Resource
    private SystemInitService systemInitService;

    @Override
    public void reload() {
        systemInitService.initParms();
    }
}
