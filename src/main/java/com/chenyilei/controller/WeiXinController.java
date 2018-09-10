package com.chenyilei.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/weixin")
@Slf4j
public class WeiXinController {

    @RequestMapping("/auth")
    public void auth(@RequestParam(value = "code",required = false)String code ){
        log.info("进入auth"+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) );
        log.info(code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx8400dc20e725e7d3&" +
                "secret=23c32e1ddd96618605307b472062c3b5&code="+code+ "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url,String.class);

        log.info(response);
    }
}
