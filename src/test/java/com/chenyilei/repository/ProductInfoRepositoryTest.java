package com.chenyilei.repository;

import com.chenyilei.dataobject.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    ProductInfoRepository repository;

    @Test
    public void save(){
        ProductInfo productInfo  = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setCategoryType(1);
        productInfo.setProductDescription("好吃");
        productInfo.setProductIcon("xxx.icon");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal("3.2"));
        productInfo.setProductStatus(0);
        productInfo.setProductStock(100);

        repository.save(productInfo);
    }

    @Test
    public void findByProductStatus() {

        System.out.println( repository.findByProductStatus(0) );
    }
}