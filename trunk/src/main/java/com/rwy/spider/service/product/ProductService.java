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
     * 查看最新的结果
     * @param bean
     * @param pageView
     * @return
     */
    public PageView getAnalyseResult(ProductBean bean, PageView pageView,Class clazz) throws Exception;

    @Deprecated
    public List<Product> isProductExist(String md5code);

    @Deprecated
    public void updateProduct(Product oldProduct,Product newProduct);

    /**
     * 获取要导出的列表
     * @param bean
     * @param ids
     * @param clazz
     * @return
     */
    public List<ProductDto> getExportResultList(ProductBean bean,String[] ids,Class clazz);

    /**
     * 提取分析结果作为email数据
     * @return
     */
    public Map getProductForEmail();
}
