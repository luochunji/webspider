package com.rwy.spider.service.platform;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.service.base.DAO;

import java.util.Map;

/**
 * Created by Luocj on 2014/10/23.
 */
public interface PlatFormService extends DAO<PlatForm> {

    public Map<Long,String> getPlatFormMap();
}
