package com.rwy.spider.dto;

import com.rwy.spider.bean.product.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Luocj on 2014/11/3.
 */
public class TaskResultListDto implements Serializable {

    private String keyword;

    private List<Product> productList;


}
