package com.chenyilei.dataobject;

import com.chenyilei.enums.ProductStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
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
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    //类目 编号
   private Integer categoryType;

   private Date createTime;

   private Date updateTime;

}
