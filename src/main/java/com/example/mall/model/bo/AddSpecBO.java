package com.example.mall.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname AddSpecBO
 * @Description
 * @Date 2022-08-23 10:51
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class AddSpecBO {

    private String goodsId;
    private String specName;
    private String stockNum;
    private String unitPrice;
}
