package com.rwy.spider.bean.agency;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Luocj on 2014/11/7.
 *
 * 分销商
 */
@Entity
@Table(name="T_AGENCY")
public class Agency implements Serializable{

    private String id;

    /**
     * 分销商用户名
     */
    private String userName;

    /**
     * 分销商全称
     */
    private String name;

    /**
     * 所属平台
     */
    private Long platFormId;


    /**
     * 状态（0-关闭过滤，1-开启过滤）
     */
    private String status;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    private Set<AgencyStore> agencyStores = new HashSet<AgencyStore>();

    public void addAgencyStore(AgencyStore as){
        this.agencyStores.add(as);
        as.setAgency(this);
    }

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Column(length = 64)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    @Column(length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(length = 64)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @OneToMany(mappedBy="agency",cascade={CascadeType.REMOVE,CascadeType.PERSIST})
    public Set<AgencyStore> getAgencyStores() {
        return agencyStores;
    }

    public void setAgencyStores(Set<AgencyStore> agencyStores) {
        this.agencyStores = agencyStores;
    }
}
