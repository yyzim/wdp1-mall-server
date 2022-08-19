package com.example.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname AddAdminssBO
 * @Description
 * @Date 2022-08-19 21:56
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddAdminssBO {

    private String nickname;
    private String email;
    private String pwd;
}
