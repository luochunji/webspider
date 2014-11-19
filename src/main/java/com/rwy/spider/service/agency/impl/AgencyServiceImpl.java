package com.rwy.spider.service.agency.impl;

import com.rwy.spider.bean.agency.Agency;
import com.rwy.spider.service.agency.AgencyService;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.web.bean.AgencyBean;
import com.rwy.spider.web.common.PageView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
