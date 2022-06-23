package com.zc.store.controller;

import com.zc.store.entity.Product;
import com.zc.store.service.IProductService;
import com.zc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    IProductService productService;

    @RequestMapping("/details")
    public JsonResult getHostGoodsList() {
        List<Product> products = productService.selectHostGoods();

        return new JsonResult(OK, products);
    }

    @RequestMapping("/newdetails")
    public JsonResult getNewGoodsList() {
        List<Product> products = productService.selectNewGoods();

        return new JsonResult(OK, products);
    }

    @RequestMapping("/{id}/good")
    public JsonResult findById(@PathVariable("id") Integer id) {
        Product product = productService.findById(id);

        return new JsonResult(OK, product);
    }
}
