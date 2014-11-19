package com.rwy.spider.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解：用于导出EXCEL时自动填充中文行头
 * Created by Luocj on 2014/11/19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelExportRuleAnnotation {
    String label();
    String getValueMethod() default "";
}
