package com.chenyilei.utils;

import com.chenyilei.VO.ResultVO;

import java.util.List;

public class ResultVOUtil {

    public static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(data);
        resultVO.setMsg("成功");
        resultVO.setCode(0);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }
}
