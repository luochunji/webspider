package com.rwy.spider.web.action;

import com.rwy.spider.bean.product.Product;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.platform.PlatFormService;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.service.scenic.ScenicService;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.common.PageView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Luocj on 2014/11/13.
 */
@Controller
@RequestMapping("/product")
public class ProductAction {

    @Resource
    public ProductService productService;
    @Resource
    public PlatFormService platFormService;
    @Resource
    public ScenicService scenicService;


    @RequestMapping("/list")
    public String list(@ModelAttribute("productForm") ProductBean bean,ModelMap modelMap){
        PageView<Product> pageView = new PageView<Product>(12,bean.getPage());
        modelMap.put("pageView", productService.getAnalyseResult(bean, pageView));
        modelMap.put("scenicMap",scenicService.getScenicMap());
        modelMap.put("platFormMap", Constant.PLATFORM_MAP);
        modelMap.put("bean",bean);
        return "product/list";
    }
}
