package com.chenyilei.form;

import lombok.Data;

/**
 * @author chenyilei
 * @date 2018/9/12/0012-20:17
 * hello everyone
 */
@Data
public class CategoryForm {
    private Integer categoryId;
    /**
     * 类目名字
     */
    private String categoryName;
    /**
     * 类目编号
     */
    private Integer categoryType ;
}
