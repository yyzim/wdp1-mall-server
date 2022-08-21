package com.example.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname ChangeOrderBO
 * @Description
 * @Date 2022-08-21 17:31
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangeOrderBO {

    private String id;
    private Integer state;
    private Integer spec;
    private Integer num;
}
