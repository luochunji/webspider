package com.rwy.spider.service.agency;

import com.rwy.spider.bean.agency.Agency;
import com.rwy.spider.bean.product.Product;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.base.DAO;
import com.rwy.spider.web.bean.AgencyBean;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.common.PageView;
import com.rwy.spider.web.dto.AgencyDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Luocj on 2014/10/29.
 */
public interface AgencyService extends DAO<Agency> {

    /**
     * 展示分销商列表
     * @param bean
     * @param pageView
     * @return
     */
    public PageView getAgencyList(AgencyBean bean, PageView pageView);

    /**
     * 导出分销商数据
     * @param ids
     * @return
     */
    public List<AgencyDto> getExportResultList(AgencyBean bean,String[] ids);

}
