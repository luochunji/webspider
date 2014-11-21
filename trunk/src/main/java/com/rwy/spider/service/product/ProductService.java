package com.rwy.spider.service.product;

import com.rwy.spider.bean.product.Product;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.base.DAO;
import com.rwy.spider.web.bean.ProductBean;
import com.rwy.spider.web.common.PageView;
import com.rwy.spider.web.dto.ProductDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Luocj on 2014/10/29.
 */
public interface ProductService extends DAO<Product> {

    /**
     * 查看所有结果
     * @param bean
     * @param pageView
     * @return
     */
    public PageView getProductList(ProductBean bean, PageView pageView);

    /**
     * 查看最新结果
     * @param bean
     * @param pageView
     * @return
     */
    public PageView getAnalyseResult(ProductBean bean, PageView pageView,Class clazz) throws Exception;

    public List<Product> isProductExist(String md5code);

    public void updateProduct(Product oldProduct,Product newProduct);

    public List<ProductDto> getExportResultList(ProductBean bean,String[] ids,Class clazz);


    public Map getProductForEmail();
}
