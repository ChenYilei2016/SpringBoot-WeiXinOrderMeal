package com.chenyilei.repository;

import com.chenyilei.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenyilei
 * @date 2018/9/13/0013-18:21
 * hello everyone
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {
    SellerInfo findByOpenId(String openId);
    SellerInfo findByUsername(String username);
}
