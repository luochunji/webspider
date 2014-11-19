package com.rwy.spider.service.agency;

import com.rwy.spider.bean.agency.Agency;
import com.rwy.spider.bean.product.Product;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.base.DAO;
import com.rwy.spider.web.bean.AgencyBean;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.common.PageView;

import java.util.List;
import java.util.Map;

/**
 * Created by Luocj on 2014/10/29.
 */
public interface AgencyService extends DAO<Agency> {

    public PageView getAgencyList(AgencyBean bean, PageView pageView);

}
