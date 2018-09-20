package com.chenyilei.aspect;

import com.chenyilei.exception.SellerAuthorizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author chenyilei
 * @date 2018/9/18/0018-16:06
 * hello everyone
 */
@ControllerAdvice
public class SellerExceptionHandler {

    //登陆异常
//    @ResponseStatus()
    @ExceptionHandler(SellerAuthorizeException.class)
    public ModelAndView checkLogin(){
        return new ModelAndView("redirect:/seller/to_login");
    }

}
