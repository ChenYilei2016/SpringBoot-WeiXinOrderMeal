package com.chenyilei.service.impl;

import com.chenyilei.dataobject.OrderDetail;
import com.chenyilei.dataobject.OrderMaster;
import com.chenyilei.dto.CartDTO;
import com.chenyilei.dto.OrderDTO;
import com.chenyilei.repository.OrderMasterRepository;
import com.chenyilei.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderService orderService;

    public static final String OPENID="110110";
    public static final String ORDERID="1536402940318";

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("我");
        orderMaster.setBuyerPhone("1333333");
        orderMaster.setBuyerAddress("我的网");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(0));

        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);

//        orderMasterRepository.save();
    }

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("陈家");
        orderDTO.setBuyerName("chenyilei ");
        orderDTO.setBuyerPhone("17758163513");
        orderDTO.setBuyerOpenid(OPENID);

        //子项
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO orderDTO1 = orderService.create( orderDTO);
        log.info("[创建订单]",orderDTO1);
    }

    @Test
    public void findOne() {
        System.out.println(orderService.findOne(ORDERID)  );
    }

    @Test
    public void findList() {
        Page<OrderDTO> result = orderService.findList(OPENID,new PageRequest(0,2));
        System.out.println( result  );
    }

    @Test
    public void cancel() {
        OrderDTO one = orderService.findOne(ORDERID);
        OrderDTO result = orderService.cancel(one);
        System.out.println(result);
    }

    @Test
    public void finish() {

    }

    @Test
    public void paid() {

    }
}