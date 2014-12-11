package com.rwy.spider.service.platform;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.service.base.DAO;
import com.rwy.spider.web.bean.PlatFormBean;
import com.rwy.spider.web.common.PageView;

import java.util.Map;

/**
 * Created by Luocj on 2014/10/23.
 */
public interface PlatFormService extends DAO<PlatForm> {

    public Map<Long,String> getPlatFormMap();

    /**
     * 获取平台List
     * @param bean
     * @param pageView
     * @return
     */
    public PageView getPlatFormList(PlatFormBean bean,PageView pageView);
}
