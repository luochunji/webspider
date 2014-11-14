package com.rwy.spider.bean.system;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Luocj on 2014/11/14.
 */
@Entity
@Table(name = "T_SYSTEM_PARAMS")
public class SystemParams implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 参数代码
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 是否启用 1-启用 0-关闭
     */
    private boolean status;

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(length = 64)
    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    @Column(length = 255)
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
