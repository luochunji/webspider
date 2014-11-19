package com.rwy.spider.web.action;

import com.rwy.spider.bean.scenic.Scenic;
import com.rwy.spider.service.scenic.ScenicService;
import com.rwy.spider.web.bean.ScenicBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Luocj on 2014/11/12.
 */
@Controller
@RequestMapping("/scenic")
public class ScenicAction {

    @Resource
    private ScenicService scenicService;

    @RequestMapping("/addScenicUI")
    public String addScenicUI(ModelMap modelMap,String directUrl){
        if(null!=directUrl && !"".equals(directUrl)){
            modelMap.put("directUrl",directUrl);
        }
        return "scenic/add";
    }

    @RequestMapping("/addScenic")
    public String addScenic(ScenicBean bean){
        Scenic scenic = new Scenic();
        scenic.setScenicName(bean.getScenicName());
        scenic.setStatus("1");
        scenicService.save(scenic);
        if(null!=bean.getDirectUrl() && !"".equals(bean.getDirectUrl())){
            return "redirect:/task/temp/addTaskTempUI";
        }
        return "redirect:/task/addTaskUI";
    }
}
