package com.chenyilei.dataobject;

import com.chenyilei.enums.OrderStatusEnum;
import com.chenyilei.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
public class OrderMaster {
    @Id
    private String orderId;
    //买家名字
    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    //0 新下单
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    //支付状态 默认未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();


    private Date createTime;
    private Date updateTime;
}
