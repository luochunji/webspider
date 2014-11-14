package com.rwy.spider.service.product.impl;

import com.rwy.spider.bean.product.Product;
import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.common.PageView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Luocj on 2014/10/29.
 */
@Service("productService")
@Transactional
public class ProductServiceImpl extends DaoSupport<Product> implements ProductService {

    @Override
    public PageView getProductList(ProductBean bean, PageView pageView) {
        //排序
        LinkedHashMap<String, String> orderby = buildOrder(bean.getSort());
        StringBuffer jpql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
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
        if(bean.getFilterStore()!= null && !"".equals(bean.getFilterStore())){
            if(params.size()>0) jpql.append(" and ");
            if("ourStore".equals(bean.getFilterStore())){
                jpql.append(" o.storeName in (select store.storeName from AgencyStore store)");
            }else if("otherStore".equals(bean.getFilterStore())){
                jpql.append(" o.storeName not in (select store.storeName from AgencyStore store)");
            }
        }
        if(bean.getPlatFormId()!= null && bean.getPlatFormId()!=0){
            if(params.size()>0) jpql.append(" and ");
            jpql.append(" o.platFormId=?").append((params.size()+1));
            params.add(bean.getPlatFormId());
        }
        pageView.setQueryResult(getScrollData(pageView.getFirstResult(),
                pageView.getMaxresult(), jpql.toString(), params.toArray(), orderby));

        return pageView;
    }

    @Override
    public PageView getAnalyseResult(ProductBean bean, PageView pageView) {
        //排序
        LinkedHashMap<String, String> orderby = buildOrder(bean.getSort());
        StringBuffer jpql = new StringBuffer(" 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if(bean.getFilterStore()!= null && !"".equals(bean.getFilterStore())){
            jpql.append(" and ");
            if("ourStore".equals(bean.getFilterStore())){
                jpql.append(" o.storeName in (select store.storeName from AgencyStore store)");
            }else if("otherStore".equals(bean.getFilterStore())){
                jpql.append(" o.storeName not in (select store.storeName from AgencyStore store)");
            }
        }
        if(bean.getPlatFormId()!= null && bean.getPlatFormId()!=0){
            jpql.append(" and ");
            jpql.append(" o.platFormId=?").append((params.size()+1));
            params.add(bean.getPlatFormId());
        }
        jpql.append(" and o.price < o.low_price");
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
            orderby.put("scenicId", "asc");
            orderby.put("timeStamp", "desc");
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

}
