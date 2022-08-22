package com.example.mall.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Classname OrderPO
 * @Description
 * @Date 2022-08-21 15:10
 * @Created by Yang Yi-zhou
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPO {
    private Integer id;
    private Integer userId;
    private String nickname;
    private String receiver;
    private String address;
    private String phone;
    private String goodsName;
    private Integer goodsId;
    private String specName;
    private Integer specId;
    private Integer number;
    private Double amount;
    //0 未付款 1 未发货 2 已发货 3 已到货
    private Integer stateId;
    private Timestamp createTime;
    private Boolean hasComment;
    private Double score;
}
