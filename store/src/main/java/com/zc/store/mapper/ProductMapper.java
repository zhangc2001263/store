package com.zc.store.mapper;

import com.zc.store.entity.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> selectHostGoods();

    List<Product> selectNewGoods();

    Product findById(Integer id);
}
