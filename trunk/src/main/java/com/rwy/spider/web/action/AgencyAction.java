package com.rwy.spider.web.action;

import com.rwy.spider.bean.agency.Agency;
import com.rwy.spider.bean.agency.AgencyStore;
import com.rwy.spider.bean.task.Task;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.agency.AgencyService;
import com.rwy.spider.service.agency.AgencyStoreService;
import com.rwy.spider.service.platform.PlatFormService;
import com.rwy.spider.utils.ExportExcelUtils;
import com.rwy.spider.web.bean.AgencyBean;
import com.rwy.spider.web.common.PageView;
import com.rwy.spider.web.dto.AgencyDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Luocj on 2014/10/23.
 */
@Controller
@RequestMapping("/agency")
public class AgencyAction {

    @Resource
    private AgencyService agencyService;

    @Resource
    private AgencyStoreService agencyStoreService;

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
        modelMap.put("bean",bean);
        return "agency/list";
    }

    /**
     * 添加分销商
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping("/addAgency")
    public String addAgency(@ModelAttribute("addAgencyForm") AgencyBean bean) throws Exception{

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
    @RequestMapping("/modifyAgencyUI")
    public void modifyAgencyUI(String id ,ModelMap modelMap,PrintWriter printWriter){
        Agency agency = agencyService.find(id);
        modelMap.put("agency",agency);
        modelMap.put("agencyStore",agency.getAgencyStores());
        modelMap.put("pfMap", Constant.PLATFORM_MAP);
        modelMap.put("result", "success");
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.setIgnoreDefaultExcludes(true);
        jsonConfig.setAllowNonStringKeys(true);
        JSONObject json =JSONObject.fromObject(modelMap, jsonConfig);
        printWriter.write(json.toString());
        printWriter.flush();
        printWriter.close();
    }

    @RequestMapping("/modifyAgency")
    public String modifyAgency(@ModelAttribute("modifyAgencyForm") AgencyBean bean) throws Exception{
        Agency agency = agencyService.find(bean.getAgencyId());
        agency.setUserName(bean.getUserName());
        agency.setPlatFormId(bean.getPlatFormId());
        agency.setName(bean.getName());
        Iterator it = agency.getAgencyStores().iterator();
        while(it.hasNext()){
            AgencyStore store = (AgencyStore) it.next();
            it.remove();
            agencyStoreService.delete(store.getId());
        }
        JSONArray jarray = JSONArray.fromObject(bean.getStoreInfo());
        for(Object obj : jarray){
            JSONObject jobj = (JSONObject) obj;
            AgencyStore as = new AgencyStore();
            as.setStoreName(jobj.getString("storeName"));
            as.setStoreUrl(jobj.getString("storeUrl"));
            agency.addAgencyStore(as);
            agencyStoreService.save(as);
        }
        agencyService.update(agency);
        return "redirect:/agency/list";
    }

    @RequestMapping("/delAgency")
    public String delAgency(@ModelAttribute("agencyForm")AgencyBean bean){
        if(StringUtils.isNotEmpty(bean.getIds())){
            String[] ids = bean.getIds().split(",");
            agencyService.delete(ids);
        }
        return "redirect:/agency/list";
    }

    @RequestMapping("/exportExcel")
    public void exportResult(AgencyBean bean, HttpServletResponse response) {
        String[] ids = null;
        if (null != bean.getIds() && !"".equals(bean.getIds())) {
            ids = bean.getIds().split(",");
        }
        List<AgencyDto> dtoList = agencyService.getExportResultList(bean,ids);
        ExportExcelUtils.exportExcelToBrowser(response, AgencyDto.class, dtoList, "分销商列表.xls",true);
    }
}
