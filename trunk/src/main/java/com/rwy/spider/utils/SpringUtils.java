package com.rwy.spider.utils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by Luocj on 2014/10/23.
 */
public class SpringUtils extends SpringBeanAutowiringSupport {
    /**
     * 自动装配注解会让Spring通过类型匹配为beanFactory注入示例
     */
    @Autowired
    private BeanFactory beanFactory;

    private SpringUtils() {
    }

    private static SpringUtils instance;

    static {
        // 静态块，初始化实例
        instance = new SpringUtils();
    }

    /**
     * 实例方法
     * 使用的时候先通过getInstance方法获取实例
     *
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {
        return beanFactory.getBean(beanId);
    }

    public static SpringUtils getInstance() {
        return instance;
    }
}
