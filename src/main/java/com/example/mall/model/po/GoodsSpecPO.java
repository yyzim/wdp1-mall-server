package com.example.mall.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname GoodsSpecPO
 * @Description
 * @Date 2022-08-20 13:13
 * @Created by Yang Yi-zhou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsSpecPO implements Serializable {
    private Integer id;
    private Integer goodsId;
    private String name;
    private Integer stockNum;
    private Double price;
}
