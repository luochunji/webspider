package com.rwy.spider.service.system;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.bean.task.TaskRuntime;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.platform.PlatFormService;
import com.rwy.spider.service.task.SchedulerService;
import com.rwy.spider.service.task.TaskRuntimeService;
import com.rwy.spider.utils.CronExpConversionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Luocj on 2014/11/7.
 */
@Service("systemInitService")
public class SystemInitService {

    @Resource
    private PlatFormService platFormService;
    @Resource
    private SystemParamsService systemParamsService;
    @Resource
    private SchedulerService schedulerService;
    @Resource
    private TaskRuntimeService taskRuntimeService;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SystemInitService.class);

    @PostConstruct
    public void initParms(){
        loadPlatForm();
        loadSystemParams();
    }

    private void loadSystemParams() {
        List<SystemParams> systemParamsList = systemParamsService.getScrollData().getResultlist();
        for(SystemParams params : systemParamsList){
            Constant.SYSTEM_PARAMS.put(params.getParamKey().toUpperCase(),params);
        }
        Constant.TEMP_DATA_CLEAR = Integer.valueOf(Constant.SYSTEM_PARAMS.get("TEMP_DATA_CLEAR").getParamValue());
        Constant.HISTORY_DATA_CLEAR = Integer.valueOf(Constant.SYSTEM_PARAMS.get("HISTORY_DATA_CLEAR").getParamValue());
        SystemParams param = Constant.SYSTEM_PARAMS.get("SYS_TASK_RUNTIME");
        //加载系统参数的时候设置系统任务运行时间
        if(null!=param){
            List<TaskRuntime> trList = taskRuntimeService.getSysTaskRunTime();
            TaskRuntime tr = null;
            if(0==trList.size()){
                tr = new TaskRuntime();
                tr.setRuntime(param.getParamValue());
                tr.setType("SYS");
            }else{
                tr = trList.get(0);
                tr.setRuntime(param.getParamValue());
            }
            taskRuntimeService.update(tr);
            String cronExpression= CronExpConversionUtils.getCronExpression(tr.getRuntime());
            schedulerService.schedule(tr.getId(),cronExpression);
        }
    }

    private void loadPlatForm() {
        List<PlatForm> platFormList = platFormService.getScrollData().getResultlist();
        for(PlatForm pf : platFormList){
            if("0".equals(pf.getStatus())){
                continue;
            }
            Constant.PLATFORM_MAP.put(pf.getId(),pf);
        }
    }
}
