package com.chenyilei.controller;

import com.chenyilei.dto.OrderDTO;
import com.chenyilei.enums.ResultEnum;
import com.chenyilei.exception.SellException;
import com.chenyilei.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenyilei
 * @date 2018/9/10/0010-18:52
 * hello everyone
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    public void create(@RequestParam("orderId")String orderId,
                        @RequestParam("returnUrl")String returnUrl ){
        OrderDTO orderDTO =  orderService.findOne(orderId);
        if(null == orderDTO){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //发起支付

    }
}
