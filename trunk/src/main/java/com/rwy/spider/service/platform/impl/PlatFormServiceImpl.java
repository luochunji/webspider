package com.rwy.spider.service.platform.impl;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.platform.PlatFormService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Luocj on 2014/10/23.
 */
@Service("platFormService")
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
}
