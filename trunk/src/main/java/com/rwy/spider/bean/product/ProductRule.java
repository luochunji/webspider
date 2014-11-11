package com.rwy.spider.bean.product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Luocj on 2014/10/31.
 */
//@Entity
//@Table(name = "T_RULE")
public class ProductRule  implements Serializable{

    private String id;

    private String name;

    private Product product;

    private Set<ProductRuleDetail> rules = new HashSet<ProductRuleDetail>();

    public ProductRule(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public ProductRule() {
    }

    public void addProductRuleDetail(ProductRuleDetail productRuleDetail){
        this.rules.add(productRuleDetail);
        productRuleDetail.setProductRule(this);
    }

    @Id
    @Column(length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, optional=false)
    @JoinColumn(name="productId")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToMany(mappedBy="productRule",cascade=CascadeType.ALL)
    public Set<ProductRuleDetail> getRules() {
        return rules;
    }

    public void setRules(Set<ProductRuleDetail> rules) {
        this.rules = rules;
    }
}
