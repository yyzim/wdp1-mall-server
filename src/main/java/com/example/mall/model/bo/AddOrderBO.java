package com.example.mall.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname AddOrderBO
 * @Description
 * @Date 2022-08-22 15:59
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class AddOrderBO {

    private String token;
    private Integer goodsDetailId;
    private Integer state;
    private Integer num;
    private Double amount;
}
