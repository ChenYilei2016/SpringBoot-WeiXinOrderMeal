package com.chenyilei.service.impl;

import com.chenyilei.dto.OrderDTO;
import com.chenyilei.service.PayService;
import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

/**
 * @author chenyilei
 * @date 2018/9/10/0010-19:04
 * hello everyone
 */
public class PayServiceImpl implements PayService {


    @Override
    public void create(OrderDTO orderDTO) {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();

    }
}
