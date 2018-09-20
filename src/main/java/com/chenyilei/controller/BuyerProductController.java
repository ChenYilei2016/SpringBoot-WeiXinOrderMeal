package com.chenyilei.controller;

import com.chenyilei.VO.ProductInfoVO;
import com.chenyilei.VO.ProductVO;
import com.chenyilei.VO.ResultVO;
import com.chenyilei.dataobject.ProductCategory;
import com.chenyilei.dataobject.ProductInfo;
import com.chenyilei.service.CategoryService;
import com.chenyilei.service.ProductService;
import com.chenyilei.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 买家商品
 */

@Controller
@ResponseBody
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 显示 商品列表(类目)
     * @return
     */
    @RequestMapping("/list")
    @Cacheable(cacheNames = "product",key = "123")
    public ResultVO list(){
        //1查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2 查询类目
        List<Integer> categoryTypeList = new ArrayList<>();
        for ( ProductInfo temp: productInfoList ){
            categoryTypeList.add( temp.getCategoryType() );
        }
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3 数据拼装
        //data :
        List<ProductVO> productVOList = new ArrayList<>();

        for( ProductCategory productCategory : productCategoryList ){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            //foods:
            List<ProductInfoVO> infoList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if( productInfo.getCategoryType().equals(productCategory.getCategoryType()) ){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO );
                    infoList.add(productInfoVO);
                }
            }
            productVO.setFoodsList(infoList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }
}
