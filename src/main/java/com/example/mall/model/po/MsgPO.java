package com.example.mall.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Classname MsgPO
 * @Description
 * @Date 2022-08-21 18:31
 * @Created by Yang Yi-zhou
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgPO implements Serializable {
    private Integer id;
    private String content;
    private String replyContent;
    private Integer state;
    private Timestamp createTime;
    private Timestamp replyTime;
    private Integer goodsId;
    private Integer userId;
}
