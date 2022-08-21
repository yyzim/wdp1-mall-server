package com.example.mall.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname OrdersByPageBO
 * @Description
 * @Date 2022-08-21 14:17
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class OrdersByPageBO {

    private Integer state;
    private Integer currentPage;
    private Integer pagesize;
    private String moneyLimit1;
    private String moneyLimit2;
    private String goods;
    private String address;
    private String name;
    private String id;
}
