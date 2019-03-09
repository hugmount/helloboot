package com.hugmount.helloboot.product.controller;

import com.alibaba.fastjson.JSON;
import com.hugmount.helloboot.product.pojo.ProductInfo;
import com.hugmount.helloboot.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: Li Huiming
 * @Date: 2019/3/9
 */

@Controller
@ResponseBody
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/getProductList")
    public String getProductList(ProductInfo productInfo){
        List<ProductInfo> productList = productService.getProductList(productInfo);
        String listStr = JSON.toJSONString(productList);
        log.info(listStr);
        return listStr;
    }

}