package com.chenyilei.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chenyilei.dataobject.OrderDetail;
import com.chenyilei.dto.CartDTO;
import com.chenyilei.dto.OrderDTO;
import com.chenyilei.form.OrderForm;
import lombok.Data;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid( orderForm.getOpenid() );
        orderDTO.setBuyerPhone( orderForm.getPhone());
        orderDTO.setBuyerName( orderForm.getName() );
        orderDTO.setBuyerAddress( orderForm.getAddress() );

        List<OrderDetail> orderDetailList = JSONArray.parseArray(orderForm.getItems(), OrderDetail.class);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Test
    public void testt(){

        String orderId = "1111";
        Map<String,Object> map = new HashMap<>();
        map.put("or",orderId);
        System.out.println (JSON.toJSON(map));
    }

}
