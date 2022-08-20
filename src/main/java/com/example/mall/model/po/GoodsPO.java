package com.example.mall.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname GoodsPO
 * @Description
 * @Date 2022-08-20 11:21
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoodsPO {

    private Integer id;
    private String img;
    private String name;
    private String description;
    private Double price;
    private Integer typeId;
}
