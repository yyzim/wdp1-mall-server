package com.example.mall.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname GoodsTypePO
 * @Description
 * @Date 2022-08-20 10:45
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoodsTypePO implements Serializable {

    private Integer id;
    private String name;
}
