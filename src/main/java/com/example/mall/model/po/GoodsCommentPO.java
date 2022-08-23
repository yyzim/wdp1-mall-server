package com.example.mall.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Classname GoodsCommentPO
 * @Description
 * @Date 2022-08-22 17:28
 * @Created by Yang Yi-zhou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCommentPO implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer orderId;
    private Integer goodsId;
    private Integer specId;
    private String content;
    private Double score;
    private Timestamp createTime;
}
