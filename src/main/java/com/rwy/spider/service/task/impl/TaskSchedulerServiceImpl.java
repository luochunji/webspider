package com.rwy.spider.service.task.impl;

import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.dto.QrtzTriggersDto;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.task.TaskSchedulerService;
import com.rwy.spider.web.common.PageView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Luocj on 2014/10/22.
 */
@Service("taskSchedulerService")
@Transactional
public class TaskSchedulerServiceImpl extends DaoSupport<TaskScheduler> implements TaskSchedulerService {

    /*@Override
    @Deprecated
    public PageView getTaskList(PageView pageView) {
        String jpql = "select new com.rwy.spider.dto.TaskListDto(ts,qt.nextFireTime,qt.prevFireTime,qt.triggerState) " +
                "from TaskScheduler ts ,QrtzTriggers qt " +
                "where concat(ts.id,'') = qt.id.triggerName";
        String countjpql = "select count(ts.id) from TaskScheduler ts,QrtzTriggers qt where concat(ts.id,'')  = qt.id.triggerName";
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        orderby.put("ts.id", "asc");
        orderby.put("ts.taskStatus", "desc");
        pageView.setQueryResult(this.getCustomerScrollData(pageView.getFirstResult(),
                pageView.getMaxresult(), jpql, countjpql, null, orderby));
        return pageView;
    }*/

    @Override
    public PageView getTaskList(PageView pageView) {
        String jpql = "select new com.rwy.spider.dto.TaskListDto(" +
                "ts,"+
                "(select min(qt.nextFireTime) from QrtzTriggers qt ,TaskScheduler ts where qt.id.triggerGroup = ts.id )," +
                "(select max(qt.prevFireTime) from QrtzTriggers qt ,TaskScheduler ts where qt.id.triggerGroup = ts.id )," +
                "'null') " +
                "from TaskScheduler ts";
        String countjpql = "select count(ts.id) from TaskScheduler ts";
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        orderby.put("ts.id", "asc");
        orderby.put("ts.taskStatus", "desc");
        pageView.setQueryResult(this.getCustomerScrollData(pageView.getFirstResult(),
                pageView.getMaxresult(), jpql, countjpql, null, orderby));
        return pageView;
    }

    @Override
    public TaskScheduler getTaskSchedulerByTriggerGroup(String triggerGroup) {
        String jpql = " o.id = ?1";
        List<Object> params = new ArrayList<Object>();
        params.add(triggerGroup);
        List<TaskScheduler> tsList = getScrollData(-1,-1,jpql,params.toArray()).getResultlist();
        if(tsList.size()==1){
            return tsList.get(0);
        }
        return null;
    }

    @Override
    public List<TaskExecution> getTaskDetail(String taskId) {
        TaskScheduler taskScheduler = this.find(taskId);
        List<TaskExecution> teList = new ArrayList<TaskExecution>();
        teList.addAll(taskScheduler.getTes());
        return teList;
    }

    public List<QrtzTriggersDto> getQrtzTriggersByGroup(String triggerGroup){
        String sql = "SELECT * FROM QRTZ_TRIGGERS WHERE TRIGGER_GROUP=?1";
        Query q = em.createNativeQuery(sql);
        q.setParameter(1,triggerGroup);
        List list = q.getResultList();
        List<QrtzTriggersDto> qtList = new ArrayList<QrtzTriggersDto>();
        for(Object obj : list){
            Object[] objs = (Object[]) obj;
            QrtzTriggersDto qtDto = new QrtzTriggersDto();
            qtDto.setSchedName(objs[0].toString());
            qtDto.setTriggerGroup(objs[1].toString());
            qtDto.setTriggerName(objs[2].toString());
            qtList.add(qtDto);
        }
        return qtList;
    }

    @Override
    public boolean updateTask(TaskScheduler taskScheduler, String operate) {
        if("pasuse".equals(operate)){

        }else if("resume".equals(operate)){

        }else if("remove".equals(operate)){

        }
        return false;
    }
}
