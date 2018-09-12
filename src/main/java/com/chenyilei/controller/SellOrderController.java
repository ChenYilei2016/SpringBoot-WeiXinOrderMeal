package com.chenyilei.controller;

import com.chenyilei.dto.OrderDTO;
import com.chenyilei.enums.ResultEnum;
import com.chenyilei.exception.SellException;
import com.chenyilei.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author chenyilei
 * @date 2018/9/11/0011-12:39
 * hello everyone
 */
@Controller
@RequestMapping("/seller/order")
public class SellOrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/list")
    public ModelAndView orderList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                  @RequestParam(value = "size",defaultValue = "2")Integer size,
                                  Map map){

        PageRequest request = new PageRequest(page-1,size);
        Page<OrderDTO> list = orderService.findList(request);

        map.put("orderDTOPage",list);
        map.put("currentPage",page);
        return new ModelAndView("order/list",map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(String orderId,Map map){
        OrderDTO orderDTO = orderService.findOne(orderId);
        map.put("url","/sell/seller/order/list");

        if(null == orderDTO){
//            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
            map.put("msg",ResultEnum.ORDER_NOT_EXIST.getMessage());
            return new ModelAndView("common/error",map);
        }

        orderService.cancel(orderDTO);
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(String orderId,Map map){
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(String orderId,Map map){
        OrderDTO orderDTO = orderService.findOne(orderId);
        map.put("url","/sell/seller/order/list");

        if(null == orderDTO){
            map.put("msg",ResultEnum.ORDER_NOT_EXIST.getMessage());
            return new ModelAndView("common/error",map);
        }

        orderService.finish(orderDTO);
        return new ModelAndView("common/success",map);
    }
}
