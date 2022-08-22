package com.example.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname AskGoodsMsgBO
 * @Description
 * @Date 2022-08-22 14:22
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AskGoodsMsgBO {

    private String token;
    private String msg;
    private String goodsId;
}
