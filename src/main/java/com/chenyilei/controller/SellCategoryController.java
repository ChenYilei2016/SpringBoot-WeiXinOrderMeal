package com.chenyilei.controller;

import com.chenyilei.dataobject.ProductCategory;
import com.chenyilei.form.CategoryForm;
import com.chenyilei.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2018/9/12/0012-19:48
 * hello everyone
 */

@Controller
@RequestMapping("/seller/category")
public class SellCategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public ModelAndView list(Map map){
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list",map);
    }

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false)Integer categoryId,
                              Map<String,Object> map){
        if(categoryId != null){
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    @RequestMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map map){
        if( bindingResult.hasErrors() ){
            map.put("url","/sell/seller/category/index");
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("common/error",map);
        }
        ProductCategory productCategory = new ProductCategory();
        if(!StringUtils.isEmpty(form.getCategoryId() )){
            productCategory = categoryService.findOne(form.getCategoryId());
        }else{
            //什么都不用干 因为自动增加Key
        }
        BeanUtils.copyProperties(form,productCategory);
        categoryService.save(productCategory);
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
