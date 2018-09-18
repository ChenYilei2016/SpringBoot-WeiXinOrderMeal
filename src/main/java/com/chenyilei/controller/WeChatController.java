package com.chenyilei.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

//获取openid
//
//        重定向到 /sell/wechat/authorize
//
//        参数
//        returnUrl: http://xxx.com/abc  //【必填】

//        返回
//        http://xxx.com/abc?openid=oZxSYw5ldcxv6H0EU67GgSXOUrVg
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    /**
     * 配置好的service
     */
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    //TODO:

    // http://sellchenyilei.mynatapp.cc/sell/wechat/authorize
    // http://sellchenyilei.mynatapp.cc/sell/wechat/authorize?returnUrl=http://www.baidu.com

    //临时定义 转向 获取 openid的 接口
    private static final String redirectUrl = "http://sellchenyilei.mynatapp.cc/sell/wechat/userInfo";
    //重定向到 获取用户信息
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl ){
        String resultUrl = wxMpService.oauth2buildAuthorizationUrl(redirectUrl,WxConsts.OAUTH2_SCOPE_USER_INFO,URLEncoder.encode(returnUrl));
        log.info("[微信网页授权]获取code,result={}",resultUrl);
        // /!!
        return "redirect:" + resultUrl  ;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,
                            @RequestParam("state")String state){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken= null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + state + "?openid="+  openId;
    }

    /**
     * 公众平台 登陆账号
     */
    //http://sellchenyilei.mynatapp.cc/sell/wechat/qrAuthorize?returnUrl=http://www.baidu.com
    public static final String redirectUrl2="http://sellchenyilei.mynatapp.cc/sell/wechat/qrUserInfo";

    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl")String returnUrl ){
        String resultUrl = wxOpenService.buildQrConnectUrl(redirectUrl2,WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN,URLEncoder.encode(returnUrl));
        log.info("[微信网页登陆]获取code,result={}",resultUrl);
        // /!!
        return "redirect:" + resultUrl  ;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code")String code,
                           @RequestParam("state")String state){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken= null;
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + state + "?openid="+  openId;
    }
}
