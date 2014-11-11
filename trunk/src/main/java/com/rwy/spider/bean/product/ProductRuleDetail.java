package com.rwy.spider.bean.product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Luocj on 2014/10/31.
 */
//@Entity
//@Table(name="T_RULE_DETAIL")
public class ProductRuleDetail implements Serializable{

    private String id;

    private String ruleName;

    private String ruleDesc;

    private ProductRule productRule;

    public ProductRuleDetail(String ruleName, String ruleDesc) {
        this.id = UUID.randomUUID().toString();
        this.ruleName = ruleName;
        this.ruleDesc = ruleDesc;
    }

    public ProductRuleDetail() {
    }

    @Id
    @Column(length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(length = 4000)
    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String name) {
        this.ruleName = name;
    }

    @Column(length = 4000)
    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, optional=false)
    @JoinColumn(name="ruleId")
    public ProductRule getProductRule() {
        return productRule;
    }

    public void setProductRule(ProductRule productRule) {
        this.productRule = productRule;
    }
}
