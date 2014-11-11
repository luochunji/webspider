package com.rwy.spider.web.action;

import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.dto.QrtzTriggersDto;
import com.rwy.spider.dto.TaskListDto;
import com.rwy.spider.service.platform.PlatFormService;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.service.task.SchedulerService;
import com.rwy.spider.service.task.TaskSchedulerService;
import com.rwy.spider.utils.CronExpConversionUtils;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.bean.TaskBean;
import com.rwy.spider.web.common.PageBean;
import com.rwy.spider.web.common.PageView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Luocj on 2014/10/23.
 */
@Controller
@RequestMapping("/agency")
public class AgencyAction {

    @Resource
    private SchedulerService schedulerService;
    @Resource
    private TaskSchedulerService taskSchedulerService;
    @Resource
    private PlatFormService platFormService;
    @Resource
    private ProductService productService;


    @RequestMapping("/detail")
    public String detail(@RequestParam("taskId") String taskId,ModelMap modelMap){
        TaskScheduler taskScheduler = taskSchedulerService.find(taskId);
        List<TaskExecution> teList = taskSchedulerService.getTaskDetail(taskId);
        modelMap.put("list", teList);
        modelMap.put("task", taskScheduler);
        return "task/detail";
    }

    /**
     * 展现任务处理结果
     * @param bean
     * @param modelMap
     * @return
     */
    @RequestMapping("/showResult")
    public String showResult(@ModelAttribute("productForm") ProductBean bean , ModelMap modelMap){
        PageView<TaskListDto> pageView = new PageView<TaskListDto>(12,bean.getPage());
        modelMap.put("pageView", productService.getProductList(bean, pageView));
        modelMap.put("taskId",bean.getTaskId());
        modelMap.put("bean",bean);
        modelMap.put("taskExecute",taskSchedulerService.getTaskDetail(bean.getTaskId()));
        modelMap.put("platFormMap",Constant.PLATFORM_MAP);
        modelMap.put("validsMap", Constant.TAOBAO_VALID_MAP);
        return "task/result";
    }

    /**
     * 跳转至添加任务页面
     */
    @RequestMapping("/addTaskUI")
    public String addTaskUI(ModelMap modelMap){
        modelMap.put("pfMap",platFormService.getPlatFormMap());
        return "task/add";
    }

    /**
     * 添加任务方法
     * @param taskBean
     * @return
     * @throws Exception
     */
    @RequestMapping("/addTask")
    public String addTask(@ModelAttribute("taskForm") TaskBean taskBean) throws Exception{

        TaskScheduler ts = new TaskScheduler();
        ts.setTaskName(taskBean.getTaskName());
        ts.setCreateTime(new Date());
        ts.setCreator("luocj");
        ts.setTaskStatus(Constant.TASK_STATUS_RUNNING);
        ts.setStartTime(DateUtils.parseDate(taskBean.getStartTime(),"yyyy-MM-dd"));
        ts.setEndTime(DateUtils.parseDate(taskBean.getEndTime(),"yyyy-MM-dd"));
        ts.setSendMail(taskBean.isSendMail());
        ts.setEmail(taskBean.getEmail());

        JSONArray jsonArray = JSONArray.fromObject(taskBean.getJsonArray());
        for(int i=0;i<jsonArray.size();i++){
            JSONObject json = (JSONObject) jsonArray.get(i);
            TaskExecution te = (TaskExecution) json.toBean(json,TaskExecution.class);
            te.setPlatform(platFormService.find(json.getLong("platformId")));
            ts.addTaskExecution(te);
        }
        taskSchedulerService.save(ts);
        JSONArray qrtzArray = JSONArray.fromObject(taskBean.getQuartzArray());
        for(int i=0;i<qrtzArray.size();i++) {
            JSONObject json = (JSONObject) qrtzArray.get(i);
            String cronExpression = getCronExpression(taskBean,json);
            String triggerName = UUID.randomUUID().toString();
            schedulerService.schedule(triggerName,ts.getId(),cronExpression);
        }
//        taskSchedulerService.save(ts);
        return "redirect:/task/list";
    }

    /**
     * 展现任务列表
     * @param pageBean
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String list(PageBean pageBean, ModelMap modelMap){
        PageView<TaskListDto> pageView = new PageView<TaskListDto>(12,pageBean.getPage());
        modelMap.put("pageView", taskSchedulerService.getTaskList(pageView));
        return "task/list";
    }

    @RequestMapping("/pause")
    public void pause(@RequestParam("taskId") String taskId,PrintWriter printWriter){
        TaskScheduler taskScheduler = taskSchedulerService.find(taskId);

        List<QrtzTriggersDto> qtList = taskSchedulerService.getQrtzTriggersByGroup(taskScheduler.getId());
        for(QrtzTriggersDto qtDto : qtList){
            schedulerService.pauseTrigger(qtDto.getTriggerName(), qtDto.getTriggerGroup());
        }
        taskScheduler.setTaskStatus(Constant.TASK_STATUS_PAUSE);
        taskSchedulerService.update(taskScheduler);

        JSONObject json = new JSONObject();
        json.put("result","success");
        json.put("message","操作成功，任务已暂停!");
        printWriter.write(json.toString());
        printWriter.flush();
        printWriter.close();
    }
    @RequestMapping("/resume")
    public void resume(@RequestParam("taskId") String taskId,PrintWriter printWriter){
        TaskScheduler taskScheduler = taskSchedulerService.find(taskId);

        List<QrtzTriggersDto> qtList = taskSchedulerService.getQrtzTriggersByGroup(taskScheduler.getId());
        for(QrtzTriggersDto qtDto : qtList){
            schedulerService.resumeTrigger(qtDto.getTriggerName(), qtDto.getTriggerGroup());
        }
        taskScheduler.setTaskStatus(Constant.TASK_STATUS_RUNNING);
        taskSchedulerService.update(taskScheduler);

        JSONObject json = new JSONObject();
        json.put("result","success");
        json.put("message","操作成功，任务已恢复!");
        printWriter.write(json.toString());
        printWriter.flush();
        printWriter.close();
    }
    @RequestMapping("/remove")
    public String remove(@RequestParam("taskId") String taskId){
        schedulerService.removeTrigdger(taskId);
        return "redirect:/task/list";
    }

    /**
     * 获取调度时间表达式
     * @param bean
     * @return
     */
    private String getCronExpression(TaskBean bean, JSONObject json) {
        String cronExpression = "";
        String[] commonNeeds = {json.getString("second"), json.getString("minute"), json.getString("hour")};
        String[] monthlyNeeds = {json.getString("week"), json.getString("dayOfMonth")};
        String weeklyNeeds = json.getString("dayOfWeek");
        String everyWhat = bean.getEveryWhat();
        // 得到时间规则
        cronExpression = CronExpConversionUtils.convertDateToCronExp(everyWhat, commonNeeds,
                monthlyNeeds, weeklyNeeds, null);
        return cronExpression;
    }
}
