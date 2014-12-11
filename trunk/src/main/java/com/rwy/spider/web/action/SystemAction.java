package com.rwy.spider.web.action;

import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.service.platform.PlatFormService;
import com.rwy.spider.service.system.SystemParamsService;
import com.rwy.spider.web.bean.ParamsBean;
import com.rwy.spider.web.bean.PlatFormBean;
import com.rwy.spider.web.common.PageView;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.PrintWriter;

/**
 * Created by Luocj on 2014/11/25.
 */
@Controller
@RequestMapping("/system")
public class SystemAction {

    @Resource
    private SystemParamsService systemParamsService;
    @Resource
    private PlatFormService platFormService;

    @RequestMapping("/param/list")
    public String list(ParamsBean bean,ModelMap modelMap){
        PageView<SystemParams> pageView = new PageView<SystemParams>(12,bean.getPage());
        modelMap.put("pageView",systemParamsService.getParamsList(bean,pageView));
        return "system/param/list";
    }
    @RequestMapping("/param/modifyParamsUI")
    public void modifyParamsUI(String id,ModelMap modelMap,PrintWriter printWriter){
        SystemParams param = systemParamsService.find(id);
        modelMap.put("param",param);
        modelMap.put("result","success");
        JSONObject json = JSONObject.fromObject(modelMap);
        printWriter.write(json.toString());
        printWriter.flush();
        printWriter.close();
    }
    @RequestMapping("/param/modifyParams")
    public String modifyParams(ParamsBean bean,ModelMap modelMap){
        SystemParams param = systemParamsService.find(bean.getParamId());
        param.setParamValue(bean.getParamValue());
        systemParamsService.update(param);
        systemParamsService.reload();
        return "redirect:/system/param/list";
    }
    @RequestMapping("/platform/list")
    public String pflist(PlatFormBean bean,ModelMap modelMap){
        PageView<SystemParams> pageView = new PageView<SystemParams>(12,bean.getPage());
        modelMap.put("pageView",platFormService.getPlatFormList(bean,pageView));
        return "system/platform/list";
    }

}
