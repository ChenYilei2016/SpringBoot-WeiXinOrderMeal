package com.chenyilei.service.impl;

import com.chenyilei.dataobject.OrderDetail;
import com.chenyilei.dataobject.OrderMaster;
import com.chenyilei.dataobject.ProductInfo;
import com.chenyilei.dto.CartDTO;
import com.chenyilei.dto.OrderDTO;
import com.chenyilei.enums.OrderStatusEnum;
import com.chenyilei.enums.PayStatusEnum;
import com.chenyilei.enums.ResultEnum;
import com.chenyilei.exception.SellException;
import com.chenyilei.repository.OrderDetailRepository;
import com.chenyilei.repository.OrderMasterRepository;
import com.chenyilei.service.OrderService;
import com.chenyilei.service.ProductService;
import com.chenyilei.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductService productService;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        BigDecimal orderAmount =new BigDecimal(0);
        String orderId = KeyUtil.genUniqueKey();

        //1 查询商品的数量 价格
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());

            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //计算订单总价//2 计算总价
            orderAmount = orderAmount.add(
                    productInfo.getProductPrice().multiply( new BigDecimal(orderDetail.getProductQuantity()) )  );

            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }
        //3 保存
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4 减少数据(库存)
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList() ){
            CartDTO temp = new CartDTO();
            temp.setProductId(orderDetail.getProductId());
            temp.setProductQuantity(orderDetail.getProductQuantity());
            cartDTOList.add(temp);
        }
        productService.decreaseStock(cartDTOList);

        orderDTO.setOrderId(orderId);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if( null == orderMaster){
            throw  new  SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        if(orderDetailList == null || orderDetailList.size() == 0){
            throw  new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
//        PageRequest pageRequest = new PageRequest(0,5);
        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);
        //不用显示详情,因为只显示 订单列表
        Page<OrderDTO> result = (Page<OrderDTO>)(Page)orderMasters; //转换还可以加个转换器类
        return result;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = (List<OrderDTO>)(List)orderMasters.getContent();
        return new PageImpl<OrderDTO>(orderDTOList ,pageable,orderMasters.getTotalElements());
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {

        //1判断订单状态正确否
        if( !OrderStatusEnum.NEW.getCode().equals( orderDTO.getOrderStatus() ) ){
            //不是新的订单
            log.info("[取消订单]订单状态不正确orderId={}",orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderResult = orderMasterRepository.save(orderMaster);
        if(null == orderResult){
            //更新失败
            throw  new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        //3返回 库存
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(OrderDetail orderDetail :orderDTO.getOrderDetailList()){
            cartDTOList.add(new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity())  );
        }
        productService.increaseStock(cartDTOList);

        //如果支付 -->退款
        //TODO:退款
        if( orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS) ){

        }

       return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //1判断订单状态
        if(! OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus() ) ){
            log.error("[完结订单]错误的订单状态");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2 修改状态
        orderDTO.setOrderStatus( OrderStatusEnum.FINISHED.getCode() );
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMasterRepository.save(orderMaster);

       return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //1判断订单状态
        if(! OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus() ) ){
            log.error("[支付订单状态]错误的订单状态");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        if(! PayStatusEnum.WAIT.getCode().equals( orderDTO.getPayStatus() ) ){
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }

        //2 修改状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMasterRepository.save(orderMaster);

        return orderDTO;
    }
}
