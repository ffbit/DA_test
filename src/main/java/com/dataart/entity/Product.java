package com.dataart.entity;

import com.dataart.util.IJsonable;
import com.google.gson.JsonObject;

import javax.persistence.*;

@Entity
@Table(name = "T_PRODUCT")
public class Product implements IJsonable{
    @Id
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "GROUP_ID")
    private Long groupId;

    @Column(name = "PRODUCT_PRICE")
    private Float price;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public JsonObject toJson() {
        JsonObject product = new JsonObject();

        product.addProperty("id", getId());
        product.addProperty("name", getName());
        product.addProperty("price", getPrice());
        return product;
    }
}

