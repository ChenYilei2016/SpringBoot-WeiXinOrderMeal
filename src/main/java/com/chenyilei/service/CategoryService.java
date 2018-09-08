package com.chenyilei.service;

import com.chenyilei.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {
    //找一个
    ProductCategory findOne(Integer categoryId);

    //查出所有的分类
    List<ProductCategory> findAll();

    //根据类型查找出分类
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    //保存一个分类
    ProductCategory save(ProductCategory productCategory);
}
