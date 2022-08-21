package com.example.mall.model.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Classname MsgPO
 * @Description
 * @Date 2022-08-21 18:31
 * @Created by Yang Yi-zhou
 */

@Data
@NoArgsConstructor
public class MsgPO {
    private Integer id;
    private String content;
    private String replyContent;
    private Integer state;
    private Timestamp createTime;
    private Integer goodsId;
    private Integer userId;
}
