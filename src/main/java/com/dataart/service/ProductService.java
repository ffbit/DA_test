package com.dataart.service;

import com.dataart.dao.ProductDao;
import com.dataart.dao.ProductGroupDao;
import com.dataart.entity.Product;
import com.dataart.entity.ProductGroup;
import com.dataart.util.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductGroupDao productGroupDao;

    @Transactional
    public List<ProductGroup> findGroups(){
        return productGroupDao.findAll();
    }

    @Transactional
    public JsonObject getProductsPageAsJson(Long groupId, Long pageIndex, Long pageSize, String sortColumnName, String sortDirection) {

        if (pageIndex == null)
            pageIndex = Constants.DEFAULT_PAGE_INDEX;

        if (sortColumnName == null || !(sortColumnName.length() > 0))
            sortColumnName = Constants.DEFAULT_SORT_COLUMN_NAME;

        if (sortDirection == null || !(sortDirection.length() > 0))
            sortDirection = Constants.ASC_SORT_DIRECTION;

        if (pageSize == null)
            pageSize = Constants.PAGE_BATCH_SIZE;

        List<Product> products = productDao.getProductsPage(groupId, pageIndex, pageSize, sortColumnName, sortDirection);

        //if no products were found
        if (products.isEmpty()) {
            JsonObject result = new JsonObject();
            result.addProperty("hasPreviousPage", false);
            result.addProperty("hasNextPage", false);
            result.add("products", new JsonArray());
            return result;
        }

        JsonArray productsJsonArray = new JsonArray();
        int productsSize = products.size();
        for (Product product : products.subList(0, (productsSize <= pageSize) ? productsSize : productsSize - 1))
            productsJsonArray.add(product.toJson());

        JsonObject result = new JsonObject();
        result.addProperty("hasPreviousPage", ((pageIndex == 1L) ? false : true));
        result.addProperty("hasNextPage", ((productsSize > pageSize) ? true : false));
        result.add("products", productsJsonArray);
        return result;
    }

}
