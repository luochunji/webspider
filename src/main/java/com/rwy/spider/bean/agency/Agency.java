package com.rwy.spider.bean.agency;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Luocj on 2014/11/7.
 *
 * 分销商
 */
@Entity
@Table(name="T_AGENCY")
public class Agency {

    private String id;

    /**
     * 分销商内部编号
     */
    private String agencyId;

    /**
     * 所属平台
     */
    private Long platFormId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 状态（0-关闭过滤，1-开启过滤）
     */
    private String status;

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
    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    @Column(length = 64)
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String name) {
        this.storeName = name;
    }

    @Column(length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPlatFormId() {
        return platFormId;
    }

    public void setPlatFormId(Long platFormId) {
        this.platFormId = platFormId;
    }
}
