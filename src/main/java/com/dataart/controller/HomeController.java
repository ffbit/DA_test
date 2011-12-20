package com.dataart.controller;

import com.dataart.service.ProductService;
import com.dataart.util.Constants;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "home")
    public String home(Model model){

        model.addAttribute("groups", productService.findGroups());
        return "home";
    }

    @ResponseBody
    @RequestMapping(value = "products-page", method = RequestMethod.GET)
    public String getProducts(@RequestParam(value = "groupId", required = true)Long categoryId,
                              @RequestParam(value = "pageIndex", required = false)Long pageIndex,
                              @RequestParam(value = "sortColumnName", required = false) String sortColumnName,
                              @RequestParam(value = "sortDirection", required = false) String sortDirection){

        JsonObject result =  productService.getProductsPageAsJson(categoryId,pageIndex, Constants.PAGE_BATCH_SIZE, sortColumnName, sortDirection);

        return result.toString();
    }

}
