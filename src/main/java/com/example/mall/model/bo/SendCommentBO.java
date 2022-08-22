package com.example.mall.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname SendCommentBO
 * @Description
 * @Date 2022-08-22 17:31
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class SendCommentBO {

    private String token;
    private Integer orderId;
    private Integer goodsId;
    private Integer goodsDetailId;
    private String content;
    private Integer score;
}
