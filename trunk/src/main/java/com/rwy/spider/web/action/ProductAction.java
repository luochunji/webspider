package com.rwy.spider.web.action;

import com.rwy.spider.bean.product.Product;
import com.rwy.spider.bean.product.ProductHistory;
import com.rwy.spider.bean.product.ProductResult;
import com.rwy.spider.bean.product.ProductTempResult;
import com.rwy.spider.bean.system.SystemParams;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.mail.impl.MailServiceImpl;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.service.scenic.ScenicService;
import com.rwy.spider.service.task.TaskService;
import com.rwy.spider.utils.ExportExcelUtils;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.bean.TaskBean;
import com.rwy.spider.web.common.PageView;
import com.rwy.spider.web.dto.ProductDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Luocj on 2014/11/13.
 */
@Controller
@RequestMapping("/product")
public class ProductAction {

    @Resource
    private ProductService productService;
    @Resource
    private ScenicService scenicService;
    @Resource
    private MailServiceImpl mailService;
    @Resource
    private TaskService taskService;


    @RequestMapping("/showResult")
    public String showResult(@ModelAttribute("productForm") ProductBean bean,ModelMap modelMap) throws Exception{
        PageView<Product> pageView = new PageView<Product>(12,bean.getPage());
        bean.setClazz("ProductResult");
        bean.setUrl("showResult");
        modelMap.put("pageView", productService.getAnalyseResult(bean, pageView, ProductResult.class));
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("platFormMap", Constant.PLATFORM_MAP);
        modelMap.put("bean",bean);
        modelMap.put("taskCount",taskService.getTaskList(new TaskBean(),new PageView(12,bean.getPage())).getTotalrecord());
        return "product/list";
    }

    @RequestMapping("/showHistory")
    public String showHistory(@ModelAttribute("productForm") ProductBean bean,ModelMap modelMap) throws Exception{
        PageView<Product> pageView = new PageView<Product>(12,bean.getPage());
        bean.setClazz("ProductHistory");
        bean.setUrl("showHistory");
        modelMap.put("pageView", productService.getAnalyseResult(bean, pageView, ProductHistory.class));
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("platFormMap", Constant.PLATFORM_MAP);
        modelMap.put("bean",bean);
        return "product/list";
    }

    @RequestMapping("/showTempResult")
    public String showTempResult(@ModelAttribute("productForm") ProductBean bean,ModelMap modelMap) throws Exception{
        PageView<Product> pageView = new PageView<Product>(12,bean.getPage());
        bean.setClazz("ProductTempResult");
        bean.setUrl("showTempResult");
        modelMap.put("pageView", productService.getAnalyseResult(bean, pageView, ProductTempResult.class));
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("platFormMap", Constant.PLATFORM_MAP);
        modelMap.put("bean",bean);
        return "product/list";
    }

    @RequestMapping("/exportExcel")
    public void exportResult(ProductBean bean,HttpServletResponse response){
        try {
            String[] ids = null;
            if(null!=bean.getIds() && !"".equals(bean.getIds())){
                ids = bean.getIds().split(",");
            }
            Class clazz = Class.forName("com.rwy.spider.bean.product."+bean.getClazz());
            List<ProductDto> dtoList = productService.getExportResultList(bean,ids,clazz);
            String fileName= "常规任务结果导出.xls";
            if("ProductTempResult".equals(bean.getClazz())){
                fileName= "临时任务结果导出.xls";
            }else if("ProductHistory".equals(bean.getClazz())){
                fileName= "历史结果导出.xls";
            }
            ExportExcelUtils.exportExcelToBrowser(response,ProductDto.class,dtoList,fileName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/sendMail")
    public void exportResultForMail() throws Exception{
        SystemParams params = (SystemParams) Constant.SYSTEM_PARAMS.get("EMAIL");
        if(null != params && StringUtils.isNotEmpty(params.getParamValue())){
            String[] emails = params.getParamValue().split(";");
            mailService.execute(emails,productService.getProductForEmail());
        }

    }
}
