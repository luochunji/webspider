package com.rwy.spider.service.task.impl;

import com.rwy.spider.bean.task.Task;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.task.SchedulerService;
import com.rwy.spider.service.task.TaskService;
import com.rwy.spider.web.bean.TaskBean;
import com.rwy.spider.web.common.PageView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

        jpql.append(" select distinct new com.rwy.spider.web.dto.TaskListDto(s,o) from Scenic s,Task o where s.id = o.scenicId");

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

        jpql.append(" select distinct new com.rwy.spider.web.dto.TaskListDto(s,o,r)");
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

    private LinkedHashMap<String, String> buildOrder(String sort) {
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        if("pricedesc".equals(sort)){
            orderby.put("price", "desc");
        }else if("priceasc".equals(sort)){
            orderby.put("price", "asc");
        }else{
            orderby.put("o.scenicId", "asc");
        }
        return orderby;
    }
}
