package com.rwy.spider.service.system;

import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.service.base.DAO;

/**
 * Created by Luocj on 2014/11/14.
 */
public interface SystemParamsService extends DAO<SystemParams> {

    public void reload();

}
