package com.rwy.spider.service.agency.impl;

import com.rwy.spider.bean.agency.Agency;
import com.rwy.spider.service.agency.AgencyService;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.web.bean.AgencyBean;
import com.rwy.spider.web.common.PageView;
import com.rwy.spider.web.dto.AgencyDto;
import com.rwy.spider.web.dto.TaskTempDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Luocj on 2014/11/11.
 */
@Service("agencyService")
@Transactional
public class AgencyServiceImpl extends DaoSupport<Agency> implements AgencyService {

    @Override
    public PageView getAgencyList(AgencyBean bean, PageView pageView) {
        //排序
        LinkedHashMap<String, String> orderby = buildOrder(null);
        StringBuffer jpql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();

        String condition = bean.getCondition();
        String keyword = bean.getKeyword();

        if("storeName".equals(condition)){
            jpql.append(" select o from Agency o,AgencyStore astore");
            jpql.append(" where o.id = astore.agency.id ");
            jpql.append(" and astore.storeName like ?").append(params.size()+1);
            params.add("%"+keyword+"%");

            pageView.setQueryResult(getCustomerScrollData(pageView.getFirstResult(),
                    pageView.getMaxresult(), jpql.toString(),"",params.toArray(), orderby));
        }else{
            //按分销商编号查询
            if("agencyId".equals(condition)){
                if(params.size()>0) jpql.append(" and ");
                jpql.append(" o.agencyId=?").append((params.size()+1));
                params.add(keyword);
            }
            //按分销商用户名查询
            if("username".equals(condition)){
                if(params.size()>0) jpql.append(" and ");
                jpql.append(" o.username=?").append((params.size()+1));
                params.add(keyword);
            }
            //按分销商全称查询
            if("name".equals(condition)){
                if(params.size()>0) jpql.append(" and ");
                jpql.append(" o.name like ?").append((params.size()+1));
                params.add("%"+ keyword+ "%");
            }
            //按网店名称查询
            if("storeName".equals(condition)){
                if(params.size()>0) jpql.append(" and ");
                jpql.append(" o.storeName like ?").append((params.size()+1));
                params.add("%"+ keyword+ "%");
            }

            pageView.setQueryResult(getScrollData(pageView.getFirstResult(),
                    pageView.getMaxresult(), jpql.toString(), params.toArray(), orderby));
        }

        return pageView;
    }

    @Override
    public List<AgencyDto> getExportResultList(AgencyBean bean,String[] ids) {
        StringBuffer jpql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
        jpql.append(" select new com.rwy.spider.web.dto.AgencyDto(o.userName,o.name,s.storeName,s.storeUrl)");
        jpql.append(" from Agency o,AgencyStore s");
        jpql.append(" where o.id = s.agency.id ");
        if(null!=ids && 0!=ids.length){
            jpql.append(" and o.id in (?").append(params.size()+1).append(")");
            params.add(Arrays.asList(ids));
        }
        if(null!=bean.getKeyword() && !"".equals(bean.getKeyword())){
            jpql.append(" and ");
            jpql.append(" concat(s.storeName,o.userName,o.name) like ?").append(params.size()+1);
            params.add("%"+ bean.getKeyword() +"%");
        }
        jpql.append(" order by o.userName ");
        Query query = em.createQuery(jpql.toString());
        for(int i=0;i<params.size();i++){
            query.setParameter(i+1, params.get(i));
        }
        List<AgencyDto> dtoList = query.getResultList();
        return dtoList;
    }

    private LinkedHashMap<String, String> buildOrder(String sort) {
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        if("pricedesc".equals(sort)){
            orderby.put("price", "desc");
        }else if("priceasc".equals(sort)){
            orderby.put("price", "asc");
        }else{
            orderby.put("status", "DESC");
            orderby.put("userName", "asc");
        }
        return orderby;
    }
}
