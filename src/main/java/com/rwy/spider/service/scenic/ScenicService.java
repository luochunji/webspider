package com.rwy.spider.service.scenic;

import com.rwy.spider.bean.scenic.Scenic;
import com.rwy.spider.service.base.DAO;

import java.util.Map;

/**
 * Created by Luocj on 2014/11/12.
 */
public interface ScenicService extends DAO<Scenic>{

    /**
     * 获取所有景区
     * @return MAP
     */
    public Map<String, String> getScenicMap();

}
