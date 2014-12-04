package com.rwy.spider.service.system.impl;

import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.system.SystemInitService;
import com.rwy.spider.service.system.SystemParamsService;
import com.rwy.spider.web.bean.ParamsBean;
import com.rwy.spider.web.common.PageView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


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

    @Override
    public PageView getParamsList(ParamsBean bean, PageView pageView) {
        //排序
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        orderby.put("id","asc");
        StringBuffer jpql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();

        String description = bean.getDescription();

        if(null!=description && !"".equals(description)){
            if(params.size()>1) jpql.append(" and ");
            jpql.append(" o.description like ?").append(params.size()+1);
            params.add("%"+ description +"%");
        }
        pageView.setQueryResult(getScrollData(pageView.getFirstResult(),pageView.getMaxresult(),jpql.toString(),params.toArray(),orderby));
        return pageView;
    }

}
