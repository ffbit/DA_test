package com.dataart.dao;

import com.dataart.entity.ProductGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductGroupDao extends BaseDao {

    public List<ProductGroup> findAll() {
        return getJpaTemplate().find("from ProductGroup order by name");
    }
}
