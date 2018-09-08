package com.chenyilei.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class OrderDetail {

    @Id
    private String detailId;

    private String orderId;

    private String productId;

    private String productName;

    //单价
    private BigDecimal productPrice;

    //买的个数
    private Integer productQuantity;

    private String productIcon;
}
