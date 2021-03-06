package com.chenyilei.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "wechat")
@Component
@Data
public class WechatMpProperties {

    private String mpAppId;
    private String mpAppSecret;

    private String openAppId;
    private String openAppSecret;

    //商户号
    private String mchId;

    //商户 密匙
    private String mchKey;

    //商户 证书路径
    private String keyPath;

}