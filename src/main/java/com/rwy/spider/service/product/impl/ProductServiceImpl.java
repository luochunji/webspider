package com.rwy.spider.service.product.impl;

import com.rwy.spider.bean.product.Product;
import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.common.PageView;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Luocj on 2014/10/29.
 */
@Service("productService")
public class ProductServiceImpl extends DaoSupport<Product> implements ProductService {

    @Override
    public PageView getProductList(ProductBean bean, PageView pageView) {

        //排序
        LinkedHashMap<String, String> orderby = buildOrder(bean.getSort());
        StringBuffer jpql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
        jpql.append(" o.taskId = ?").append((params.size()+1));
        params.add(bean.getTaskId());
        //按关键字查询
        if(bean.getTaskExecuteId()!=null && !"".equals(bean.getTaskExecuteId())){
            if(params.size()>0) jpql.append(" and ");
            jpql.append(" o.taskExecutionId=?").append((params.size()+1));
            params.add(bean.getTaskExecuteId());
        }
        //按店铺名称
        if(bean.getStoreName()!=null && !"".equals(bean.getStoreName())){
            if(params.size()>0) jpql.append(" and ");
            jpql.append(" o.storeName like ?").append((params.size()+1));
            params.add("%"+ bean.getStoreName().trim()+ "%");
        }
        //按产品类型
        if(bean.getCategory()!=null &&  !"".equals(bean.getCategory())){
            if(params.size()>0) jpql.append(" and ");
            jpql.append(" o.category=?").append((params.size()+1));
            params.add(bean.getCategory());
        }
        //按产品种类
        if(bean.getType()!=null &&  !"".equals(bean.getType())){
            if(params.size()>0) jpql.append(" and ");
            jpql.append(" o.type=?").append((params.size()+1));
            params.add(bean.getType());
        }
        //按最低价格查询
        if(bean.getMinPrice()!=null &&  !"".equals(bean.getMinPrice())){
            if(params.size()>0) jpql.append(" and ");
            jpql.append(" o.price>=?").append((params.size()+1));
            params.add(Double.valueOf(bean.getMinPrice()));
        }
        if(bean.getMaxPrice()!=null &&  !"".equals(bean.getMaxPrice())){
            if(params.size()>0) jpql.append(" and ");
            jpql.append(" o.price<=?").append((params.size()+1));
            params.add(Double.valueOf(bean.getMaxPrice()));
        }
        //按有效期种类查询
        if(bean.getValidWeeks()!=null && !"".equals(bean.getValidWeeks())){
            if(params.size()>0) jpql.append(" and ");
            jpql.append(" o.validWeeks=?").append((params.size()+1));
            params.add(bean.getValidWeeks());
        }
        pageView.setQueryResult(getScrollData(pageView.getFirstResult(),
                pageView.getMaxresult(), jpql.toString(), params.toArray(), orderby));

        return pageView;
    }

    private LinkedHashMap<String, String> buildOrder(String sort) {
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        if("pricedesc".equals(sort)){
            orderby.put("price", "desc");
        }else if("priceasc".equals(sort)){
            orderby.put("price", "asc");
        }else{
            orderby.put("storeName", "asc");
            orderby.put("id", "asc");
        }
        return orderby;
    }

    @Override
    public List<Product> isProductExist(String md5code) {
        String jpql = " o.md5Code = ?1";
        List<Object> params = new ArrayList<Object>();
        params.add(md5code);
        List<Product> productList = getScrollData(-1,-1,jpql,params.toArray()).getResultlist();
        return productList;
    }

    public void updateProduct(Product oldProduct,Product newProduct){
        newProduct.setId(oldProduct.getId());
        this.update(newProduct);
    }


    @Override
    public Map getAnalyseResult(TaskScheduler taskScheduler) {
        PageView<Product> pageView = new PageView<Product>(12,1);
        Set<TaskExecution> tes = taskScheduler.getTes();
        Iterator it = tes.iterator();
        String keyword = "";
        String fileCondition = "";
        String value = "";
        Map resultMap = new HashMap();
        while (it.hasNext()){
            TaskExecution taskExecution = (TaskExecution) it.next();
            keyword = taskExecution.getKeyword();
            fileCondition = taskExecution.getFilterCondition();
            value = taskExecution.getFilterValue();
            StringBuffer jpql = new StringBuffer("");
            List<Object> params = new ArrayList<Object>();
            jpql.append(" o.taskId = ?").append((params.size()+1));
            params.add(taskScheduler.getId());
            //按店铺名称
            if(null!=fileCondition && !"".equals(fileCondition)){
                if(params.size()>0) jpql.append(" and ");
                jpql.append(" o.").append(fileCondition).append("<?").append((params.size() + 1));
                params.add(Double.valueOf(value));
                resultMap.put(keyword,getScrollData(-1,-1,jpql.toString(),params.toArray()).getResultlist());
            }

        }
        return resultMap;
    }
}
