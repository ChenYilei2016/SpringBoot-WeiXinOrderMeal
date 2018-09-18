package com.chenyilei.dataobject;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author chenyilei
 * @date 2018/9/13/0013-18:19
 * hello everyone
 */
@Entity
@Data
public class SellerInfo {

    @Id
    @Column(name = "id")
    private String sellerId;

    private String username;
    private String password;

    @Column(name = "openid")
    private String openId;

}
