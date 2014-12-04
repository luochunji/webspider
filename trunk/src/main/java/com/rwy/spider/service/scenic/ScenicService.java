package com.rwy.spider.service.scenic;

import com.rwy.spider.bean.scenic.Scenic;
import com.rwy.spider.service.base.DAO;

import java.util.List;
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


    /**
     * 根据输入的关键字联动获取inputSuggest
     * @return
     */
    public List<String> getScenicBykeyword(String keyword);

    /**
     * 根据景区名称获取景区Id
     * @param scenicName
     * @return
     */
    public String getScenicIdByName(String scenicName);

}
