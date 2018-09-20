package com.chenyilei.service.impl;

import com.chenyilei.service.PushMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author chenyilei
 * @date 2018/9/19/0019-12:39
 * hello everyone
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PushMessageImplTest {

    @Autowired
    PushMessage pushMessage ;

    @Test
    public void sendMsg() {
        pushMessage.sendMsg();
    }
}