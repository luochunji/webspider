package com.rwy.spider.service.product.impl;

import com.rwy.spider.bean.product.Product;
import com.rwy.spider.service.base.DaoSupport;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.common.PageView;
import com.rwy.spider.web.dto.ProductDto;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
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
    public PageView getAnalyseResult(ProductBean bean, PageView pageView,Class clazz) throws Exception{
        //排序
        LinkedHashMap<String, String> orderby = buildOrder(bean.getSort());
        StringBuffer jpql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
        jpql.append(" select new com.rwy.spider.web.dto.ProductDto(o.id,o.taskType,s.scenicName, o.type, o.category, o.price, o.low_price, p.name, o.productUrl, o.storeName, o.timeStamp)");
        jpql.append(" from Scenic s,PlatForm p,"+DaoSupport.getEntityName(clazz)+" o");
        jpql.append(" where s.id = o.scenicId and p.id = o.platFormId");
        if(bean.getFilterStore()!= null && !"".equals(bean.getFilterStore())){
            jpql.append(" and");
            if("ourStore".equals(bean.getFilterStore())){
                jpql.append(" o.storeName in (select store.storeName from AgencyStore store)");
            }else if("otherStore".equals(bean.getFilterStore())){
                jpql.append(" o.storeName not in (select store.storeName from AgencyStore store)");
            }
        }
        if(bean.getPlatFormId()!= null && bean.getPlatFormId()!=0){
            jpql.append(" and");
            jpql.append(" o.platFormId=?").append((params.size()+1));
            params.add(bean.getPlatFormId());
        }
        String keyword = bean.getKeyword();
        if(null!=keyword && !"".equals(keyword)){
            jpql.append(" and");
            jpql.append(" concat(s.scenicName,o.type,o.category,o.storeName) like ?").append(params.size()+1);
            params.add("%"+ keyword +"%");
        }
        if(null!=bean.getStartDate() && !"".equals(bean.getStartDate())){
            Date startDate = DateUtils.parseDate(bean.getStartDate(),"yyyy-MM-dd");
            jpql.append(" and");
            jpql.append(" o.timeStamp >=?").append(params.size()+1);
            params.add(startDate);
        }
        if(null!=bean.getEndDate() && !"".equals(bean.getEndDate())){
            Date endDate = DateUtils.parseDate(bean.getEndDate(),"yyyy-MM-dd");
            jpql.append(" and");
            jpql.append(" o.timeStamp <=?").append(params.size()+1);
            params.add(endDate);
        }
        if(null!=bean.getMinPrice() && !"".equals(bean.getMinPrice())){
            jpql.append(" and");
            jpql.append(" o.price >=?").append(params.size()+1);
            params.add(Double.valueOf(bean.getMinPrice()));
        }
        if(null!=bean.getMaxPrice() && !"".equals(bean.getMaxPrice())){
            jpql.append(" and");
            jpql.append(" o.price <=?").append(params.size()+1);
            params.add(Double.valueOf(bean.getMaxPrice()));
        }
        pageView.setQueryResult(getCustomerScrollData(pageView.getFirstResult(),
                pageView.getMaxresult(), jpql.toString(),null, params.toArray(), orderby));

        return pageView;
    }


    private LinkedHashMap<String, String> buildOrder(String sort) {
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        if("pricedesc".equals(sort)){
            orderby.put("price", "desc");
        }else if("priceasc".equals(sort)){
            orderby.put("price", "asc");
        }else{
            orderby.put("timeStamp", "desc");
            orderby.put("scenicId", "asc");
            orderby.put("storeName", "desc");
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
    public List<ProductDto> getExportResultList(ProductBean bean,String[] ids,Class clazz) {
        StringBuilder jpql = new StringBuilder("");
        List<Object> params = new ArrayList<Object>();
        jpql.append(" select new com.rwy.spider.web.dto.ProductDto(o.id,o.taskType,s.scenicName, o.type, o.category, o.price, o.low_price, p.name, o.productUrl, o.storeName, o.timeStamp)");
        jpql.append(" from Scenic s,PlatForm p,"+DaoSupport.getEntityName(clazz)+" o");
        jpql.append(" where s.id = o.scenicId and p.id = o.platFormId ");
        if(null!=ids && 0!=ids.length){
            jpql.append(" and o.id in (?").append(params.size()+1).append(")");
            params.add(Arrays.asList(ids));
        }
        if(null!=bean.getKeyword() && !"".equals(bean.getKeyword())){
            jpql.append(" and ");
            jpql.append(" concat(s.scenicName,o.type,o.category,o.storeName) like ?").append(params.size()+1);
            params.add("%"+ bean.getKeyword() +"%");
        }
        Query query = em.createQuery(jpql.toString());
        for(int i=0;i<params.size();i++){
            query.setParameter(i+1, params.get(i));
        }
        List<ProductDto> dtoList = query.getResultList();
        return dtoList;
    }

    @Override
    public Map getProductForEmail() {
        Map<String,List<ProductDto>> map = new HashMap();
        StringBuilder jpql = new StringBuilder("");
        List<Object> params = new ArrayList<Object>();
        jpql.append(" select new com.rwy.spider.web.dto.ProductDto(o.id,s.scenicName, o.type, o.category, o.price, o.low_price, p.name, o.productUrl, o.storeName,o.storeUrl, o.timeStamp)");
        jpql.append(" from Scenic s,PlatForm p,ProductResult o");
        jpql.append(" where s.id = o.scenicId and p.id = o.platFormId ");
        jpql.append(" order by o.price desc,o.scenicId asc");
        Query query = em.createQuery(jpql.toString());
        List<ProductDto> dtoList = query.getResultList();
        for(ProductDto dto : dtoList){
            String key = dto.getScenicName();
            if(map.containsKey(key)){
                map.get(key).add(dto);
            }else{
                List<ProductDto> pdList = new ArrayList<ProductDto>();
                pdList.add(dto);
                map.put(key,pdList);
            }
        }
        dtoList = null;
        return map;
    }

    @Override
    public void process(String proce,Object ... objects) {
        Query query = em.createNativeQuery(proce);
        for(int i=0;i<objects.length;i++){
            query.setParameter(i+1, objects[i]);
        }
        query.executeUpdate();
    }

}
