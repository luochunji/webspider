package com.rwy.spider.bean.platform;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Luocj on 2014/10/23.
 */
@Entity
@Table(name="T_PLATFORM")
public class PlatForm implements Serializable{

    private Long id;

    private String name;

    private String version;

    private String service;

    private String description;

    private String status;


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(length = 10)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(length = 50)
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Column(length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(length = 4000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
