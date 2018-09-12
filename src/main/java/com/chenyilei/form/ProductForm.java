package com.chenyilei.form;

import com.chenyilei.enums.ProductStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenyilei
 * @date 2018/9/12/0012-18:48
 * hello everyone
 */
@Data
public class ProductForm {
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    //库存
    private Integer productStock;

    private String productDescription;
    //小图连接
    private String productIcon;

    //类目 编号
    private Integer categoryType;
}
