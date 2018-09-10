package com.chenyilei.controller;

import com.chenyilei.VO.ResultVO;
import com.chenyilei.converter.OrderForm2OrderDTOConverter;
import com.chenyilei.dto.CartDTO;
import com.chenyilei.dto.OrderDTO;
import com.chenyilei.enums.ResultEnum;
import com.chenyilei.exception.SellException;
import com.chenyilei.form.OrderForm;
import com.chenyilei.service.OrderService;
import com.chenyilei.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    OrderService orderService;

    //创建订单
    @RequestMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm
                            , BindingResult bindingResult){
        if(bindingResult.hasErrors() ){
            log.error("[创建订单有错误]");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        //创建订单
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(orderDTO.getOrderDetailList()==null || orderDTO.getOrderDetailList().size()==0 )
        {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO orderDTOresult = orderService.create( orderDTO );
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderDTOresult.getOrderId());
        return ResultVOUtil.success(map);
    }

    //订单列表
    @PostMapping("/list")
    public ResultVO<List<OrderDTO>> list(String openid,
                                         @RequestParam(value = "page",defaultValue ="0")Integer page,
                                         @RequestParam(value = "size",defaultValue ="10")Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("[查询订单链表]openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        Page<OrderDTO> list = orderService.findList(openid, new PageRequest(page, size));
        return ResultVOUtil.success(list.getContent());
    }

    //订单详情 根据用户
    @RequestMapping("/detail")
    public ResultVO<OrderDTO> detail(String openid,
                                     String orderId){
        //安全性
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            throw new SellException(ResultEnum.OPENID_ORDER_ERRPR);
        }
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @RequestMapping("/cancel")
    public ResultVO cancel(String openid,
                                     String orderId){
        //安全性
        OrderDTO orderDTO = orderService.findOne(orderId);

        if(orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            throw new SellException(ResultEnum.OPENID_ORDER_ERRPR);
        }

        orderService.cancel(orderDTO);
        return ResultVOUtil.success();
    }

}
