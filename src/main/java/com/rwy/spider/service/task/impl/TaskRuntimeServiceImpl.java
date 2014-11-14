package com.rwy.spider.service.task.impl;

import com.rwy.spider.bean.task.TaskRuntime;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.task.TaskRuntimeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Luocj on 2014/11/13.
 */
@Service("taskRuntimeService")
@Transactional
public class TaskRuntimeServiceImpl extends DaoSupport<TaskRuntime> implements TaskRuntimeService {
    @Override
    public Map<String, TaskRuntime> getTaskRuntimeMap() {
        LinkedHashMap<String,String> orderBy = new LinkedHashMap<String, String>();
        orderBy.put("runtime","asc");
        List<TaskRuntime> trList = getScrollData(-1,-1,orderBy).getResultlist();
        Map<String,TaskRuntime> map = new LinkedHashMap<String, TaskRuntime>();
        for(TaskRuntime tr : trList){
            map.put(tr.getId(),tr);
        }
        return map;
    }

    @Override
    public Map<String, TaskRuntime> getNormalTaskRuntimeMap() {
        LinkedHashMap<String,String> orderBy = new LinkedHashMap<String, String>();
        orderBy.put("runtime","asc");
        String whereJpql = " o.type = 'NORMAL'";
        List<TaskRuntime> trList = getScrollData(-1,-1,whereJpql,null,orderBy).getResultlist();
        Map<String,TaskRuntime> map = new LinkedHashMap<String, TaskRuntime>();
        for(TaskRuntime tr : trList){
            map.put(tr.getId(),tr);
        }
        return map;
    }
}
