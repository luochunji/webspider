package com.rwy.spider.service.system;

import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.service.base.DAO;
import com.rwy.spider.web.bean.ParamsBean;
import com.rwy.spider.web.common.PageView;

/**
 * Created by Luocj on 2014/11/14.
 */
public interface SystemParamsService extends DAO<SystemParams> {

    public void reload();

    public PageView getParamsList(ParamsBean bean,PageView pageView);

}
