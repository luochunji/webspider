package com.rwy.spider.service.scenic.impl;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.bean.scenic.Scenic;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.scenic.ScenicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Luocj on 2014/11/12.
 */
@Service("scenicService")
@Transactional
public class ScenicServiceImpl extends DaoSupport<Scenic> implements ScenicService {

    @Override
    public Map<String, String> getScenicMap() {
        Map scenicMap = new LinkedHashMap();
        List<Scenic> scenicList  = this.getScrollData().getResultlist();
        for(Scenic scenic:scenicList){
            scenicMap.put(scenic.getId(),scenic.getScenicName());
        }
        return scenicMap;
    }

    @Override
    public List<String> getScenicBykeyword(String keyword) {
        StringBuilder jpql = new StringBuilder("");
        jpql.append("select o.scenicName from Scenic o where o.scenicName like ?1");
        Query query = em.createQuery(jpql.toString());
        query.setParameter(1,"%"+keyword+"%");
        List<String> list = query.getResultList();
        return list;
    }

    @Override
    public String getScenicIdByName(String scenicName) {
        StringBuilder jpql = new StringBuilder("");
        jpql.append("select o.id from Scenic o where o.scenicName = ?1");
        Query query = em.createQuery(jpql.toString());
        query.setParameter(1,scenicName);
        List<String> list = query.getResultList();
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
