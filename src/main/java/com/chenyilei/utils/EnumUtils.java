package com.chenyilei.utils;

import com.chenyilei.enums.CodeEnum;

/**
 * @author chenyilei
 * @date 2018/9/11/0011-13:51
 * hello everyone
 */
public class EnumUtils {

    public static <T extends CodeEnum>
    T getByCode(Integer code,Class<T> tClass){
        for(T temp : tClass.getEnumConstants()){
            if(code.equals( temp.getCode() ) ){
                return temp;
            }
        }
        return null;
    }
}
