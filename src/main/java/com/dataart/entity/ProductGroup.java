package com.dataart.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "T_GROUP")
public class ProductGroup {
    @Id
    @Column(name = "GROUP_ID")
    private Long id;

    @Column(name = "GROUP_NAME")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "GROUP_ID")
    private Set<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
