package com.rwy.spider.service.platform.impl;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.platform.PlatFormService;
import com.rwy.spider.web.bean.PlatFormBean;
import com.rwy.spider.web.common.PageView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Luocj on 2014/10/23.
 */
@Service("platFormService")
@Transactional
public class PlatFormServiceImpl extends DaoSupport<PlatForm> implements PlatFormService {
    @Override
    public Map<Long, String> getPlatFormMap() {

        Map platFormMap = new LinkedHashMap();
        List<PlatForm> pfList  = this.getScrollData().getResultlist();
        for(PlatForm pf:pfList){
            platFormMap.put(pf.getId(),pf.getName());
        }
        return platFormMap;
    }

    @Override
    public PageView getPlatFormList(PlatFormBean bean, PageView pageView) {
        //排序
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        orderby.put("id","asc");
        pageView.setQueryResult(getScrollData(pageView.getFirstResult(),pageView.getMaxresult(),null,null,orderby));
        return pageView;
    }
}
