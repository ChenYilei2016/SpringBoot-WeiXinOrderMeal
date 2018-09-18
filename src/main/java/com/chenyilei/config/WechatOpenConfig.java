package com.chenyilei.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyilei
 * @date 2018/9/18/0018-12:05
 * hello everyone
 */
@Configuration
public class WechatOpenConfig {

    @Autowired
    private WechatMpProperties wechatMpProperties;

    @Bean
    public WxMpService wxOpenService(){
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage( wxOpenConfigStorage() );
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage wxOpenConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId( wechatMpProperties.getOpenAppId() );
        wxMpConfigStorage.setSecret(wechatMpProperties.getOpenAppSecret());
        return wxMpConfigStorage;
    }
}
