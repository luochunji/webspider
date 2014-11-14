package com.rwy.spider.web.action;

import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.bean.task.Task;
import com.rwy.spider.bean.task.TaskRuntime;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.dto.TaskListDto;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.service.scenic.ScenicService;
import com.rwy.spider.service.system.SystemParamsService;
import com.rwy.spider.service.task.SchedulerService;
import com.rwy.spider.service.task.TaskRuntimeService;
import com.rwy.spider.service.task.TaskService;
import com.rwy.spider.utils.CronExpConversionUtils;
import com.rwy.spider.web.bean.ParamsBean;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.bean.RuntimeBean;
import com.rwy.spider.web.bean.TaskBean;
import com.rwy.spider.web.common.PageView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Luocj on 2014/10/23.
 */
@Controller
@RequestMapping("/task")
public class TaskAction {

    @Resource
    private ScenicService scenicService;
    @Resource
    private TaskService taskService;
    @Resource
    private TaskRuntimeService taskRuntimeService;
    @Resource
    private SchedulerService schedulerService;
    @Resource
    private ProductService productService;
    @Resource
    private SystemParamsService systemParamsService;

    /**
     * 跳转至添加任务页面
     */
    @RequestMapping("/addTaskUI")
    public String addTaskUI(ModelMap modelMap){
        modelMap.put("scenicMap",scenicService.getScenicMap());
        return "task/add";
    }

    @RequestMapping("/modifyTaskUI")
    public String modifyTaskUI(String id ,ModelMap modelMap){
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("task",taskService.find(id));
        return "task/modify";
    }

    @RequestMapping("/addRuntimeUI")
    public String addRuntimeUI(String id ,ModelMap modelMap){
        return "task/add_runtime";
    }

    @RequestMapping("/addTask")
    public String addTask(TaskBean bean){
        String conditions = bean.getConditions();
        JSONArray jsonArray = JSONArray.fromObject(conditions);
        for(Object obj : jsonArray){
            JSONObject jobj = (JSONObject)obj;
            Task task = new Task();
            task.setScenicId(bean.getScenicId());
            task.setKeyword(jobj.getString("keyword"));
            task.setCreateTime(new Date());
            task.setPrice(jobj.getDouble("price"));
            taskService.save(task);
        }
        return "redirect:/task/list";
    }
    @RequestMapping("/delTask")
    public String delTask(TaskBean bean){
        if(StringUtils.isNotEmpty(bean.getIds())){
            String[] ids = bean.getIds().split(",");
            taskService.delete(ids);
        }

        return "redirect:/task/list";
    }
    @RequestMapping("/addRuntime")
    public String addRuntime(String runtime){
        if(StringUtils.isNotEmpty(runtime)){
            String[] runtimes = runtime.split(",");
            for(String rt : runtimes){
                if(StringUtils.isNotEmpty(rt)){
                    TaskRuntime tr = new TaskRuntime();
                    tr.setRuntime(rt);
                    tr.setType("NORMAL");
                    taskRuntimeService.save(tr);
                    String cronExpression= getCronExpression(rt);
                    String triggerName = tr.getId();
                    //保存调度时间
                    schedulerService.schedule(triggerName,cronExpression);
                }
            }
        }

        return "redirect:/task/list";
    }
    @RequestMapping("/modifyRuntimeUI")
    public String modifyRuntimeUI(ModelMap modelMap){
        modelMap.put("runtimeMap",taskRuntimeService.getNormalTaskRuntimeMap());
        return "task/modify_runtime";
    }
    @RequestMapping("/modifyRuntime")
    public String modifyRuntime(String runtime){
        Map<String,TaskRuntime> map = taskRuntimeService.getNormalTaskRuntimeMap();
        for(String key : map.keySet()){
            TaskRuntime tr = map.get(key);
            schedulerService.removeTrigdger(tr.getId());
            taskRuntimeService.delete(tr.getId());
        }
        this.addRuntime(runtime);


        return "redirect:/task/list";
    }
    @RequestMapping("/modifyTask")
    public String modifyTask(TaskBean bean){
        Task task = taskService.find(bean.getId());
        task.setKeyword(bean.getKeyword());
        task.setPrice(bean.getPrice());
        taskService.update(task);
        return "redirect:/task/list";
    }

    /**
     * 展现任务列表
     * @param bean
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String list(@ModelAttribute("taskForm")TaskBean bean, ModelMap modelMap){
        PageView<TaskListDto> pageView = new PageView<TaskListDto>(12,bean.getPage());
        modelMap.put("pageView", taskService.getTaskList(bean,pageView));
        modelMap.put("runtimeMap",taskRuntimeService.getTaskRuntimeMap());
        modelMap.put("bean",bean);
        return "task/list";
    }

    @RequestMapping("/showResult")
    public String showResult(@ModelAttribute("productForm") ProductBean bean , ModelMap modelMap){
        PageView<TaskListDto> pageView = new PageView<TaskListDto>(12,bean.getPage());
        modelMap.put("pageView", productService.getProductList(bean, pageView));
        modelMap.put("taskId",bean.getTaskId());
        modelMap.put("bean",bean);
        modelMap.put("platFormMap", Constant.PLATFORM_MAP);
        modelMap.put("validsMap", Constant.TAOBAO_VALID_MAP);
        return "task/result";
    }

    @RequestMapping("/addEmailUI")
    public String addEmailUI(ModelMap modelMap){
        modelMap.put("paramsMap",Constant.SYSTEM_PARAMS);
        return "/task/add_email";
    }

    @RequestMapping("/saveEmail")
    public String saveEmail(@ModelAttribute("paramsForm")ParamsBean bean){
        SystemParams sp = new SystemParams();
        sp.setId(bean.getId());
        sp.setParamKey(bean.getParamKey());
        sp.setParamValue(bean.getParamValue());
        systemParamsService.update(sp);
        systemParamsService.reload();
        return "redirect:/task/list";
    }

//    @RequestMapping("/detail")
//    public String detail(@RequestParam("taskId") String taskId,ModelMap modelMap){
//        TaskScheduler taskScheduler = taskSchedulerService.find(taskId);
//        List<TaskExecution> teList = taskSchedulerService.getTaskDetail(taskId);
//        modelMap.put("list", teList);
//        modelMap.put("task", taskScheduler);
//        return "task/detail";
//    }

//    /**
//     * 展现任务处理结果
//     * @param bean
//     * @param modelMap
//     * @return
//     */
//    @RequestMapping("/showResult")
//    public String showResult(@ModelAttribute("productForm") ProductBean bean , ModelMap modelMap){
//        PageView<TaskListDto> pageView = new PageView<TaskListDto>(12,bean.getPage());
//        modelMap.put("pageView", productService.getProductList(bean, pageView));
//        modelMap.put("taskId",bean.getTaskId());
//        modelMap.put("bean",bean);
//        modelMap.put("taskExecute",taskSchedulerService.getTaskDetail(bean.getTaskId()));
//        modelMap.put("platFormMap",Constant.PLATFORM_MAP);
//        modelMap.put("validsMap", Constant.TAOBAO_VALID_MAP);
//        return "task/result";
//    }


//    /**
//     * 添加任务方法
//     * @param taskBean
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/addTask")
//    public String addTask(@ModelAttribute("taskForm") TaskBean taskBean) throws Exception{
//
//        TaskScheduler ts = new TaskScheduler();
//        ts.setTaskName(taskBean.getTaskName());
//        ts.setCreateTime(new Date());
//        ts.setCreator("luocj");
//        ts.setTaskStatus(Constant.TASK_STATUS_RUNNING);
//        ts.setStartTime(DateUtils.parseDate(taskBean.getStartTime(),"yyyy-MM-dd"));
//        ts.setEndTime(DateUtils.parseDate(taskBean.getEndTime(),"yyyy-MM-dd"));
//        ts.setSendMail(taskBean.isSendMail());
//        ts.setEmail(taskBean.getEmail());
//
//        JSONArray jsonArray = JSONArray.fromObject(taskBean.getJsonArray());
//        for(int i=0;i<jsonArray.size();i++){
//            JSONObject json = (JSONObject) jsonArray.get(i);
//            TaskExecution te = (TaskExecution) json.toBean(json,TaskExecution.class);
//            te.setPlatform(platFormService.find(json.getLong("platformId")));
//            ts.addTaskExecution(te);
//        }
//        taskSchedulerService.save(ts);
//        JSONArray qrtzArray = JSONArray.fromObject(taskBean.getQuartzArray());
//        for(int i=0;i<qrtzArray.size();i++) {
//            JSONObject json = (JSONObject) qrtzArray.get(i);
//            String cronExpression = getCronExpression(taskBean,json);
//            String triggerName = UUID.randomUUID().toString();
//            schedulerService.schedule(triggerName,ts.getId(),cronExpression);
//        }
////        taskSchedulerService.save(ts);
//        return "redirect:/task/list";
//    }

//    /**
//     * 展现任务列表
//     * @param pageBean
//     * @param modelMap
//     * @return
//     */
//    @RequestMapping("/list")
//    public String list(PageBean pageBean, ModelMap modelMap){
//        PageView<TaskListDto> pageView = new PageView<TaskListDto>(12,pageBean.getPage());
//        modelMap.put("pageView", taskSchedulerService.getTaskList(pageView));
//        return "task/list";
//    }

//    @RequestMapping("/pause")
//    public void pause(@RequestParam("taskId") String taskId,PrintWriter printWriter){
//        TaskScheduler taskScheduler = taskSchedulerService.find(taskId);
//
//        List<QrtzTriggersDto> qtList = taskSchedulerService.getQrtzTriggersByGroup(taskScheduler.getId());
//        for(QrtzTriggersDto qtDto : qtList){
//            schedulerService.pauseTrigger(qtDto.getTriggerName(), qtDto.getTriggerGroup());
//        }
//        taskScheduler.setTaskStatus(Constant.TASK_STATUS_PAUSE);
//        taskSchedulerService.update(taskScheduler);
//
//        JSONObject json = new JSONObject();
//        json.put("result","success");
//        json.put("message","操作成功，任务已暂停!");
//        printWriter.write(json.toString());
//        printWriter.flush();
//        printWriter.close();
//    }
//    @RequestMapping("/resume")
//    public void resume(@RequestParam("taskId") String taskId,PrintWriter printWriter){
//        TaskScheduler taskScheduler = taskSchedulerService.find(taskId);
//
//        List<QrtzTriggersDto> qtList = taskSchedulerService.getQrtzTriggersByGroup(taskScheduler.getId());
//        for(QrtzTriggersDto qtDto : qtList){
//            schedulerService.resumeTrigger(qtDto.getTriggerName(), qtDto.getTriggerGroup());
//        }
//        taskScheduler.setTaskStatus(Constant.TASK_STATUS_RUNNING);
//        taskSchedulerService.update(taskScheduler);
//
//        JSONObject json = new JSONObject();
//        json.put("result","success");
//        json.put("message","操作成功，任务已恢复!");
//        printWriter.write(json.toString());
//        printWriter.flush();
//        printWriter.close();
//    }
//    @RequestMapping("/remove")
//    public String remove(@RequestParam("taskId") String taskId){
//        schedulerService.removeTrigdger(taskId);
//        return "redirect:/task/list";
//    }

    /**
     * 获取调度时间表达式
     * @param runtime
     * @return
     */
    private String getCronExpression(String runtime) {
        String cronExpression = "";
        String[] time = runtime.split(":");

        String[] commonNeeds = {time[2], time[1], time[0]};
//        String[] monthlyNeeds = {json.getString("week"), json.getString("dayOfMonth")};
//        String weeklyNeeds = json.getString("dayOfWeek");
        String everyWhat = "dayly";
        // 得到时间规则
        cronExpression = CronExpConversionUtils.convertDateToCronExp(everyWhat, commonNeeds,
                null, null, null);
        return cronExpression;
    }
}
