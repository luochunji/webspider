package com.rwy.spider.web.action;

import com.rwy.spider.bean.agency.Agency;
import com.rwy.spider.bean.agency.AgencyStore;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.agency.AgencyService;
import com.rwy.spider.service.platform.PlatFormService;
import com.rwy.spider.web.bean.AgencyBean;
import com.rwy.spider.web.common.PageView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Luocj on 2014/10/23.
 */
@Controller
@RequestMapping("/agency")
public class AgencyAction {

    @Resource
    private AgencyService agencyService;

    @Resource
    private PlatFormService platFormService;

    /**
     * 展现分销商列表
     * @param bean
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String list(@ModelAttribute("agencyForm") AgencyBean bean, ModelMap modelMap){
        PageView<Agency> pageView = new PageView<Agency>(12,bean.getPage());
        modelMap.put("pageView", agencyService.getAgencyList(bean,pageView));
        modelMap.put("pfMap", Constant.PLATFORM_MAP);
        return "agency/list";
    }

    /**
     * 展现添加分销商页面
     * @param modelMap
     * @return
     */
    @RequestMapping("/addAgencyUI")
    public String addAgencyUI(ModelMap modelMap){
        modelMap.put("pfMap",Constant.PLATFORM_MAP);
        return "agency/add";
    }

    /**
     * 添加分销商
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping("/addAgency")
    public String addAgency(@ModelAttribute("agencyForm") AgencyBean bean) throws Exception{

        Agency agency = new Agency();
        agency.setName(bean.getName());
        agency.setPlatFormId(bean.getPlatFormId());
        //新增的分销商默认状态为待审核
        agency.setStatus("");
        agency.setUserName(bean.getUserName());
        agency.setCreateTime(new Date());
        agency.setCreator("luocj");
        JSONArray jarray = JSONArray.fromObject(bean.getStoreInfo());
        for(Object obj : jarray){
            JSONObject jobj = (JSONObject) obj;
            AgencyStore as = new AgencyStore();
            as.setStoreName(jobj.getString("storeName"));
            as.setStoreUrl(jobj.getString("storeUrl"));
            agency.addAgencyStore(as);
        }
        agencyService.save(agency);
        return "redirect:/agency/list";
    }
/*
    @RequestMapping("/detail")
    public String detail(@RequestParam("taskId") String taskId,ModelMap modelMap){
        TaskScheduler taskScheduler = taskSchedulerService.find(taskId);
        List<TaskExecution> teList = taskSchedulerService.getTaskDetail(taskId);
        modelMap.put("list", teList);
        modelMap.put("task", taskScheduler);
        return "task/detail";
    }

    *//**
     * 展现任务处理结果
     * @param bean
     * @param modelMap
     * @return
     *//*
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

    *//**
     * 跳转至添加任务页面
     *//*
    @RequestMapping("/addTaskUI")
    public String addTaskUI(ModelMap modelMap){
        modelMap.put("pfMap",platFormService.getPlatFormMap());
        return "task/add";
    }

    *//**
     * 添加任务方法
     * @param taskBean
     * @return
     * @throws Exception
     *//*



    *//**
     * 获取调度时间表达式
     * @param bean
     * @return
     *//*
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
    }*/
}
