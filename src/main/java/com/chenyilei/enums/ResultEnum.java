package com.chenyilei.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {

     PRODUCT_NOT_EXIST(10,"商品不存在")
    ,PRODUCT_STOCK_ERROR(11,"库存不正确")
    ,ORDER_NOT_EXIST(12,"订单不存在")
    ,ORDER_DETAIL_NOT_EXIST(13,"订单项不存在")
    ,ORDER_STATUS_ERROR(14,"订单状态不正确")
    ,ORDER_UPDATE_ERROR(15,"订单状态更新失败")
    ,PAY_STATUS_ERROR(17,"订单支付状态错误")
    ,OPENID_ORDER_ERRPR(18,"用户与订单不符")
    ,PRODUCT_STATUS_ERROR(19,"商品状态错误")

    ,PARAM_ERROR(1,"参数不正确");

    private Integer code ;
    private String message;
    ResultEnum(Integer code,String messgage){
        this.code = code;
        this.message = messgage;
    }
}
