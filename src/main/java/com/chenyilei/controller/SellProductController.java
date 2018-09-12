package com.chenyilei.controller;

import com.chenyilei.dataobject.ProductInfo;
import com.chenyilei.service.CategoryService;
import com.chenyilei.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家商品控制
 * @author chenyilei
 * @date 2018/9/11/0011-18:21
 * hello everyone
 */
@Controller
@RequestMapping("/seller/product")
public class SellProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                  @RequestParam(value = "size",defaultValue = "2")Integer size,
                                  Map map){
        Page<ProductInfo> productInfoPage = productService.findAll(new PageRequest(page-1,size));
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        return new ModelAndView("product/list",map);
    }

    // 上架
    @GetMapping("/on_sale")
    public ModelAndView onSale(String productId,Map map){
        productService.onSale(productId);

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
    //下架
    @GetMapping("/off_sale")
    public ModelAndView offSale(String productId,Map map){
        productService.offSale(productId);

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
