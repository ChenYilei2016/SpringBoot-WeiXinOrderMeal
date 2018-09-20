package com.chenyilei.controller;

import com.chenyilei.dataobject.ProductCategory;
import com.chenyilei.dataobject.ProductInfo;
import com.chenyilei.exception.SellException;
import com.chenyilei.form.ProductForm;
import com.chenyilei.service.CategoryService;
import com.chenyilei.service.ProductService;
import com.chenyilei.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
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

    /**
     * 跳到 商品添加 更新 页面
     */
    @GetMapping("/index")
    public ModelAndView index(Map map,
                              @RequestParam(value = "productId",required = false)String productId ){

        //判断是新增 还是 更新
        //更新的话 要带着原有的信息
        if(!StringUtils.isEmpty(productId )){
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo" ,productInfo );
        }

        //查询所有选择类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);

        return new ModelAndView("product/index",map);
    }

    //更新数据
    @RequestMapping("/save")
//    @CachePut(key = "#form.productId") 返回对象不行 直接清楚
    @CacheEvict(cacheNames = "product",key = "123")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map map){
        if( bindingResult.hasErrors() ){
            map.put("url","/sell/seller/product/list");
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();;
        try {
            BeanUtils.copyProperties(form,productInfo);
            if( StringUtils.isEmpty(form.getProductId() ) ){
                productInfo.setProductId(KeyUtil.genUniqueKey());
            }else{
                productInfo = productService.findOne(form.getProductId());
                BeanUtils.copyProperties(form,productInfo);
            }
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
