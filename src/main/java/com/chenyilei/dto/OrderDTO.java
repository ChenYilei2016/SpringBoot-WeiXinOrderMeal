package com.chenyilei.dto;

import com.chenyilei.dataobject.OrderDetail;
import com.chenyilei.dataobject.OrderMaster;
import com.chenyilei.enums.OrderStatusEnum;
import com.chenyilei.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO extends OrderMaster {
//    private String orderId;
//    //买家名字
//    private String buyerName;
//
//    private String buyerPhone;
//
//    private String buyerAddress;
//
//    private String buyerOpenid;
//
//    private BigDecimal orderAmount;
//
//    //0 新下单
//    private Integer orderStatus ;
//
//    //支付状态 默认未支付
//    private Integer payStatus ;
//
//    private Date createTime;
//    private Date updateTime;

    @Transient
    List<OrderDetail> orderDetailList;
}
