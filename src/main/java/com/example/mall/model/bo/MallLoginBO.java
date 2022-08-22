package com.example.mall.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MallLoginBO
 * @Description
 * @Date 2022-08-21 21:38
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class MallLoginBO {

    private String email;
    private String pwd;
}
