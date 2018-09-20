package com.chenyilei.service.impl;

import com.chenyilei.service.PushMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author chenyilei
 * @date 2018/9/19/0019-12:27
 * hello everyone
 */

@Service
@Slf4j
public class PushMessageImpl implements PushMessage {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 根据 获取的 openid 发送对应的消息
     */
    @Override
    public void sendMsg() {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("um5WqyrGQH4iwkMaMj74niw1AI-3MotvPeOqo9zsbWE");
        wxMpTemplateMessage.setToUser("otcdw1k3qDxiLxHhgx26Rys0qFbY");
        wxMpTemplateMessage.setData(new ArrayList<>());
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模版消息】发送失败, {}", e);
        }

    }
}
