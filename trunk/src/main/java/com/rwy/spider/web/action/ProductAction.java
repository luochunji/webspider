package com.rwy.spider.web.action;

import com.rwy.spider.bean.product.*;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.service.scenic.ScenicService;
import com.rwy.spider.utils.ExportExcelUtils;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.common.PageView;
import com.rwy.spider.web.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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


    @RequestMapping("/showResult")
    public String showResult(@ModelAttribute("productForm") ProductBean bean,ModelMap modelMap){
        PageView<Product> pageView = new PageView<Product>(12,bean.getPage());
        bean.setClazz("ProductResult");
        modelMap.put("pageView", productService.getAnalyseResult(bean, pageView, ProductResult.class));
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("platFormMap", Constant.PLATFORM_MAP);
        modelMap.put("bean",bean);
        modelMap.put("url","showResult");
        return "product/list";
    }

    @RequestMapping("/showHistory")
    public String showHistory(@ModelAttribute("productForm") ProductBean bean,ModelMap modelMap){
        PageView<Product> pageView = new PageView<Product>(12,bean.getPage());
        modelMap.put("pageView", productService.getAnalyseResult(bean, pageView, ProductHistory.class));
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("platFormMap", Constant.PLATFORM_MAP);
        modelMap.put("bean",bean);
        modelMap.put("url","showHistory");
        modelMap.put("clazz","ProductHistory");
        return "product/list";
    }

    @RequestMapping("/showTempResult")
    public String showTempResult(@ModelAttribute("productForm") ProductBean bean,ModelMap modelMap){
        PageView<Product> pageView = new PageView<Product>(12,bean.getPage());
        modelMap.put("pageView", productService.getAnalyseResult(bean, pageView, ProductTempResult.class));
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("platFormMap", Constant.PLATFORM_MAP);
        modelMap.put("bean",bean);
        modelMap.put("url","showTempResult");
        modelMap.put("clazz","ProductTempResult");
        return "product/list";
    }
    @RequestMapping("/showTempHistory")
    public String showTempHistory(@ModelAttribute("productForm") ProductBean bean,ModelMap modelMap){
        PageView<Product> pageView = new PageView<Product>(12,bean.getPage());
        modelMap.put("pageView", productService.getAnalyseResult(bean, pageView, ProductTempHistory.class));
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("platFormMap", Constant.PLATFORM_MAP);
        modelMap.put("bean",bean);
        modelMap.put("url","showTempHistory");
        modelMap.put("clazz","ProductTempHistory");
        return "product/list";
    }

    @RequestMapping("/exportExcel")
    public void exportResult(@ModelAttribute("productForm")ProductBean bean,HttpServletResponse response){
        try {
            String[] ids = null;
            if(null!=bean.getIds() && !"".equals(bean.getIds())){
                ids = bean.getIds().split(",");
            }
            Class clazz = Class.forName("com.rwy.spider.bean.product."+bean.getClazz());
            List<ProductDto> dtoList = productService.getExportResultList(ids,clazz);
            ExportExcelUtils.exportExcelToBrowser(response,ProductDto.class,dtoList,"测试文件.xls");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
