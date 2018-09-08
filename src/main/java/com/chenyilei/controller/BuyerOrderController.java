package com.chenyilei.controller;

import com.chenyilei.VO.ResultVO;
import com.chenyilei.dto.CartDTO;
import com.chenyilei.dto.OrderDTO;
import com.chenyilei.service.OrderService;
import com.chenyilei.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@ResponseBody
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    OrderService orderService;

    //创建订单
    @RequestMapping("/create")
    public ResultVO create(){
//        OrderDTO orderDTOResult = orderService.create(orderDTO);
//        String orderId = orderDTOResult.getOrderId();

        return ResultVOUtil.success();
    }

    //订单列表

    //订单详情 根据用户

    //取消订单

}
