package com.rwy.spider.web.action;

import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.bean.task.Task;
import com.rwy.spider.bean.task.TaskRuntime;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.web.dto.TaskListDto;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.service.scenic.ScenicService;
import com.rwy.spider.service.system.SystemParamsService;
import com.rwy.spider.service.task.SchedulerService;
import com.rwy.spider.service.task.TaskRuntimeService;
import com.rwy.spider.service.task.TaskService;
import com.rwy.spider.utils.CronExpConversionUtils;
import com.rwy.spider.web.bean.ParamsBean;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.bean.TaskBean;
import com.rwy.spider.web.common.PageView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

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

    /*********** 景区任务 START ***********/

    /**
     * 跳转至添加任务页面
     */
    @RequestMapping("/addTaskUI")
    public String addTaskUI(ModelMap modelMap){
        modelMap.put("scenicMap",scenicService.getScenicMap());
        return "task/add";
    }

    /**
     * 跳转至修改任务页面
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/modifyTaskUI")
    public String modifyTaskUI(String id ,ModelMap modelMap){
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("task",taskService.find(id));
        return "task/modify";
    }

    /**
     * 添加景区任务
     * @param bean
     * @return
     */
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

    /**
     * 跳转至添加任务运行时间页面
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/addRuntimeUI")
    public String addRuntimeUI(String id ,ModelMap modelMap){
        return "task/add_runtime";
    }

    /**
     * 跳转至修改任务运行时间页面
     * @param modelMap
     * @return
     */
    @RequestMapping("/modifyRuntimeUI")
    public String modifyRuntimeUI(ModelMap modelMap){
        modelMap.put("runtimeMap",taskRuntimeService.getNormalTaskRuntimeMap());
        return "task/modify_runtime";
    }
    /**
     * 添加任务运行时间
     * @param runtime
     * @return
     */
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

    /**
     * 修改任务运行时间
     * @param runtime
     * @return
     */
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

    /**
     * 删除景区任务
     * @param bean
     * @return
     */
    @RequestMapping("/delTask")
    public String delTask(TaskBean bean){
        if(StringUtils.isNotEmpty(bean.getIds())){
            String[] ids = bean.getIds().split(",");
            taskService.delete(ids);
        }

        return "redirect:/task/list";
    }

    /**
     * 修改景区任务
     * @param bean
     * @return
     */
    @RequestMapping("/modifyTask")
    public String modifyTask(TaskBean bean){
        Task task = taskService.find(bean.getId());
        task.setKeyword(bean.getKeyword());
        task.setPrice(bean.getPrice());
        taskService.update(task);
        return "redirect:/task/list";
    }

    /**
     * 展现景区任务列表
     * @param bean
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String list(@ModelAttribute("taskForm")TaskBean bean, ModelMap modelMap){
        PageView<TaskListDto> pageView = new PageView<TaskListDto>(12,bean.getPage());
        modelMap.put("pageView", taskService.getTaskList(bean,pageView));
        modelMap.put("runtimeMap",taskRuntimeService.getNormalTaskRuntimeMap());
        modelMap.put("bean",bean);
        return "task/list";
    }

    /**
     * 跳转至添加邮箱页面
     * @param modelMap
     * @return
     */
    @RequestMapping("/addEmailUI")
    public String addEmailUI(ModelMap modelMap){
        modelMap.put("paramsMap",Constant.SYSTEM_PARAMS);
        return "/task/add_email";
    }

    /**
     * 保存邮箱
     * @param bean
     * @return
     */
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

    /*********** 景区任务  END ***********/


    /**
     * 跳转至添加临时任务页面
     */
    @RequestMapping("/temp/addTaskTempUI")
    public String addTaskTempUI(ModelMap modelMap){
        modelMap.put("scenicMap",scenicService.getScenicMap());
        return "task/temp/add";
    }

    /**
     * 跳转至修改临时任务页面
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/temp/modifyTaskTempUI")
    public String modifyTaskTempUI(String id ,ModelMap modelMap){
        Task task = taskService.find(id);
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("task",task);
        modelMap.put("taskRt",taskRuntimeService.find(task.getRuntimeId()));
        return "task/temp/modify";
    }

    /**
     * 修改临时任务
     * @param bean
     * @return
     */
    @RequestMapping("/temp/modifyTaskTemp")
    public String modifyTaskTemp(TaskBean bean){
        Task task = taskService.find(bean.getId());
        task.setKeyword(bean.getKeyword());
        task.setPrice(bean.getPrice());
        taskService.update(task);
        return "redirect:/task/temp/list";
    }

    /**
     * 删除临时任务
     * @param bean
     * @return
     */
    @RequestMapping("/temp/delTaskTemp")
    public String delTaskTemp(TaskBean bean){
        if(StringUtils.isNotEmpty(bean.getIds())){
            String[] ids = bean.getIds().split(",");
            taskService.delete(ids);
        }
        taskRuntimeService.updateRuntimeAndTriggerJob();
        return "redirect:/task/temp/list";
    }

    /**
     * 添加临时任务
     * @param bean
     * @return
     */
    @RequestMapping("/temp/addTaskTemp")
    public String addTaskTemp(TaskBean bean){
        String runtime = bean.getRuntime();
        TaskRuntime tr = new TaskRuntime();
        tr.setRuntime(runtime);
        tr.setType("TEMP");
        taskRuntimeService.save(tr);
        schedulerService.schedule(tr.getId(),paraseStringToDate(runtime));
        String conditions = bean.getConditions();
        JSONArray jsonArray = JSONArray.fromObject(conditions);
        for(Object obj : jsonArray){
            JSONObject jobj = (JSONObject)obj;
            Task task = new Task();
            task.setScenicId(bean.getScenicId());
            task.setKeyword(jobj.getString("keyword"));
            task.setCreateTime(new Date());
            task.setPrice(jobj.getDouble("price"));
            task.setRuntimeId(tr.getId());
            task.setStatus("WATTING");
            taskService.save(task);
        }
        return "redirect:/task/temp/list";
    }


    /**
     * 展现景区临时任务列表
     * @param bean
     * @param modelMap
     * @return
     */
    @RequestMapping("/temp/list")
    public String listTemp(@ModelAttribute("taskForm")TaskBean bean, ModelMap modelMap){
        PageView<TaskListDto> pageView = new PageView<TaskListDto>(12,bean.getPage());
        modelMap.put("pageView", taskService.getTaskTempList(bean,pageView));
        modelMap.put("bean",bean);
        return "task/temp/list";
    }

    @RequestMapping("/showResult")
    public String showResult(@ModelAttribute("productForm") ProductBean bean , ModelMap modelMap){
        PageView<TaskListDto> pageView = new PageView<TaskListDto>(12,bean.getPage());
        modelMap.put("pageView", productService.getProductList(bean, pageView));
        modelMap.put("bean",bean);
        modelMap.put("platFormMap", Constant.PLATFORM_MAP);
        return "task/result";
    }


    /**
     * 获取调度时间表达式
     * @param runtime
     * @return
     */
    private String getCronExpression(String runtime) {
        String cronExpression = "";
        String[] time = runtime.split(":");

        String[] commonNeeds = {time[2], time[1], time[0]};
        String everyWhat = "dayly";
        // 得到时间规则
        cronExpression = CronExpConversionUtils.convertDateToCronExp(everyWhat, commonNeeds,
                null, null, null);
        return cronExpression;
    }

    private Date paraseStringToDate(String runtime) {
        Date rt = new Date();
        try {
            rt = DateUtils.parseDate(runtime, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rt;
    }
}
