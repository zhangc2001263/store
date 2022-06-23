package com.zc.store.service;

import com.zc.store.entity.Product;
import java.util.List;

public interface IProductService {

    /**
     * 查询热销商品的前四名
     * @return 前四名的集合
     */
    List<Product> selectHostGoods();

    /**
     * 查询最新商品的前四名
     * @return 前四名的集合
     */
    List<Product> selectNewGoods();


    /**
     * 根据id查找对应的产品
     * @param id id
     * @return 产品对象
     */
    Product findById(Integer id);
}
