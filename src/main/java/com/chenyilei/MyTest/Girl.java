package com.chenyilei.MyTest;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "girl") //省略的话 就是 girl了
@Data
public class Girl {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Integer id;

    private String cupSize ;

    private Integer age;

    public Girl(){

    }

}
