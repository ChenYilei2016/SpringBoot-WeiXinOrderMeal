package com.chenyilei.controller;

import com.chenyilei.constant.RedisConstant;
import com.chenyilei.dataobject.SellerInfo;
import com.chenyilei.repository.SellerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author chenyilei
 * @date 2018/9/18/0018-13:02
 * hello everyone
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/to_login")
    public ModelAndView toLogin(){
        return new ModelAndView("login/login");
    }

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("username") String username,
                              @CookieValue(value = "token",required = false)String tokenCookie,
                              Map map,
                              HttpServletResponse response){
//        //判断有无cookie 并且是否有效
        if(tokenCookie != null && !StringUtils.isEmpty(redisTemplate.opsForValue().get(RedisConstant.TOKEN_PREFIX+tokenCookie) )  ){
            map.put("url","/sell/seller/order/list");
            map.put("msg","已经登陆") ;
            return new ModelAndView("common/error",map);
        }

        //匹配数据库
        SellerInfo sellerInfo = sellerInfoRepository.findByUsername(username);
        if(null ==sellerInfo){
            map.put("url","/sell/seller/to_login");
            map.put("msg","错误的名字") ;
            return new ModelAndView("common/error",map);
        }
        //设置进redis //过期时间
        String token = UUID.randomUUID().toString(); //寻找的key
        Integer out_time = RedisConstant.OUT_TIME; //过期时间
        redisTemplate.opsForValue().set(RedisConstant.TOKEN_PREFIX+token,username,out_time,TimeUnit.SECONDS);

        //设置进cookie
        Cookie cookie = new Cookie("token",token);
        cookie.setMaxAge(out_time);
        cookie.setPath("/");
        response.addCookie(cookie);

        //
        return new ModelAndView("redirect:/seller/order/list",map);
    }

    @GetMapping("/logout")
    public ModelAndView logout( @CookieValue(value = "token",required = false)String tokenCookie,
                         Map map,
                         HttpServletResponse response){
        //1 cookie 查询
        if(!StringUtils.isEmpty(tokenCookie)){
            //2 清除cookie
            Cookie cookie = new Cookie("token",null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            //3 清除redis
            redisTemplate.opsForValue().getOperations().delete(RedisConstant.TOKEN_PREFIX+ tokenCookie);
        }

        return new ModelAndView("redirect:/seller/to_login");
    }

}
