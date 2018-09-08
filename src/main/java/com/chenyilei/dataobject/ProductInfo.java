package com.chenyilei.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    //库存
    private Integer productStock;

    private String productDescription;
    //小图连接
    private String productIcon;

    //状态 0正常 1下架
    private Integer productStatus;

    //类目 编号
   private Integer categoryType;

}
