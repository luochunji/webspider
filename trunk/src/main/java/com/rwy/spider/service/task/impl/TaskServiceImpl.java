package com.rwy.spider.service.task.impl;

import com.rwy.spider.bean.task.Task;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.task.SchedulerService;
import com.rwy.spider.service.task.TaskService;
import com.rwy.spider.web.bean.TaskBean;
import com.rwy.spider.web.common.PageView;
import com.rwy.spider.web.dto.TaskDto;
import com.rwy.spider.web.dto.TaskTempDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Luocj on 2014/11/12.
 */
@Service("taskService")
@Transactional
public class TaskServiceImpl extends DaoSupport<Task> implements TaskService{

    @Resource
    public SchedulerService schedulerService;

    @Override
    public PageView getTaskList(TaskBean bean, PageView pageView) {
        //排序
        LinkedHashMap<String, String> orderby = buildOrder(null);
        StringBuffer jpql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();

        String keyword = bean.getKeyword();

        jpql.append(" select distinct new com.rwy.spider.web.dto.TaskDto(o.id,s.scenicName,o.keyword,o.price,o.createTime)");
        jpql.append(" from Scenic s,Task o");
        jpql.append(" where s.id = o.scenicId and o.runtimeId is null");

        if(null!=keyword && !"".equals(keyword)){
            jpql.append(" and ");
            jpql.append(" concat(s.scenicName,o.keyword) like ?").append(params.size()+1);
            params.add("%"+ keyword +"%");
        }
        pageView.setQueryResult(getCustomerScrollData(pageView.getFirstResult(),pageView.getMaxresult(),jpql.toString(),"",params.toArray(),orderby));
        return pageView;
    }

    @Override
    public PageView getTaskTempList(TaskBean bean, PageView pageView) {

        LinkedHashMap<String, String> orderby = buildOrder(null);
        StringBuffer jpql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();

        String keyword = bean.getKeyword();

        jpql.append(" select distinct new com.rwy.spider.web.dto.TaskTempDto(o.id,s.scenicName,o.keyword,o.price,r.runtime,o.status,o.createTime)");
        jpql.append(" from Scenic s,Task o ,TaskRuntime r");
        jpql.append(" where s.id = o.scenicId ");
        jpql.append(" and o.runtimeId = r.id");
        jpql.append(" and r.type = 'TEMP'");

        if(null!=keyword && !"".equals(keyword)){
            jpql.append(" and ");
            jpql.append(" concat(s.scenicName,o.keyword) like ?").append(params.size()+1);
            params.add("%"+ keyword +"%");
        }
        pageView.setQueryResult(getCustomerScrollData(pageView.getFirstResult(),pageView.getMaxresult(),jpql.toString(),"",params.toArray(),orderby));
        return pageView;
    }

    @Override
    public List<TaskDto> getExportResultList(TaskBean bean, String[] ids) {
        LinkedHashMap<String, String> orderby = buildOrder(null);
        StringBuffer jpql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
        String keyword = bean.getKeyword();
        jpql.append(" select distinct new com.rwy.spider.web.dto.TaskDto(o.id,s.scenicName,o.keyword,o.price,o.createTime)");
        jpql.append(" from Scenic s,Task o");
        jpql.append(" where s.id = o.scenicId and o.runtimeId is null");
        if(null!=ids && 0!=ids.length){
            jpql.append(" and o.id in (?").append(params.size()+1).append(")");
            params.add(Arrays.asList(ids));
        }
        if(null!=keyword && !"".equals(keyword)){
            jpql.append(" and ");
            jpql.append(" concat(s.scenicName,o.keyword) like ?").append(params.size()+1);
            params.add("%"+ keyword +"%");
        }
        Query query = em.createQuery(jpql.toString());
        for(int i=0;i<params.size();i++){
            query.setParameter(i+1, params.get(i));
        }
        List<TaskDto> dtoList = query.getResultList();
        return dtoList;
    }

    @Override
    public List<TaskTempDto> getExportTempResultList(TaskBean bean, String[] ids) {
        LinkedHashMap<String, String> orderby = buildOrder(null);
        StringBuffer jpql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
        String keyword = bean.getKeyword();
        jpql.append(" select distinct new com.rwy.spider.web.dto.TaskTempDto(o.id,s.scenicName,o.keyword,o.price,r.runtime,o.status,o.createTime)");
        jpql.append(" from Scenic s,Task o ,TaskRuntime r");
        jpql.append(" where s.id = o.scenicId ");
        jpql.append(" and o.runtimeId = r.id");
        jpql.append(" and r.type = 'TEMP'");
        if(null!=ids && 0!=ids.length){
            jpql.append(" and o.id in (?").append(params.size()+1).append(")");
            params.add(Arrays.asList(ids));
        }
        if(null!=keyword && !"".equals(keyword)){
            jpql.append(" and ");
            jpql.append(" concat(s.scenicName,o.keyword) like ?").append(params.size()+1);
            params.add("%"+ keyword +"%");
        }
        Query query = em.createQuery(jpql.toString());
        for(int i=0;i<params.size();i++){
            query.setParameter(i+1, params.get(i));
        }
        List<TaskTempDto> dtoList = query.getResultList();
        return dtoList;
    }

    private LinkedHashMap<String, String> buildOrder(String sort) {
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        if("pricedesc".equals(sort)){
            orderby.put("price", "desc");
        }else if("priceasc".equals(sort)){
            orderby.put("price", "asc");
        }else{
            orderby.put("o.createTime", "desc");
        }
        return orderby;
    }
}
