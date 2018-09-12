package com.chenyilei.service.impl;

import com.chenyilei.dataobject.ProductInfo;
import com.chenyilei.dto.CartDTO;
import com.chenyilei.enums.ProductStatusEnum;
import com.chenyilei.enums.ResultEnum;
import com.chenyilei.exception.SellException;
import com.chenyilei.repository.ProductInfoRepository;
import com.chenyilei.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() { //
//        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());

        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if( null == productInfo ){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer leave = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(leave);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){

            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if(null == productInfo){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //库存不够卖
            Integer leave = productInfo.getProductStock()- cartDTO.getProductQuantity();
            if( leave < 0 ){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock( leave );
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if(null == productInfo){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if( productInfo.getProductStatus() == ProductStatusEnum.UP.getCode() ){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus( ProductStatusEnum.UP.getCode() );
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if(null == productInfo){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if( productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode() ){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus( ProductStatusEnum.DOWN.getCode() );
        return productInfoRepository.save(productInfo);
    }


}
