package com.chenyilei.form;

import com.chenyilei.dto.CartDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderForm {

    @NotEmpty(message = "name不能为空")
    private String name;

    private String phone;

    private String address;

    private String openid;

//    private List<OrderDetail> items;
    private String items;
}

