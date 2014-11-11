package com.rwy.spider.service.agency.impl;

import com.rwy.spider.bean.agency.Agency;
import com.rwy.spider.service.agency.AgencyService;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.common.PageView;

/**
 * Created by Luocj on 2014/11/11.
 */
public class AgencyServiceImpl extends DaoSupport<Agency> implements AgencyService {

    @Override
    public PageView getAgencyList(ProductBean bean, PageView pageView) {
        return null;
    }
}
