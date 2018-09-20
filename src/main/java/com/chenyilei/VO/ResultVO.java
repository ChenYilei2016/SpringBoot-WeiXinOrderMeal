package com.chenyilei.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 6696433506597754330L;
    private Integer code;
    private String  msg;
    private T data;
}
