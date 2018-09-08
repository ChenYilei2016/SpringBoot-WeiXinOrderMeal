package com.chenyilei.form;

import com.chenyilei.dto.CartDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderForm {

    @NotEmpty
    private String name;

    private String phone;

    private String address;

    private String openid;

//    private List<CartDTO> items;
    private String items;
}

