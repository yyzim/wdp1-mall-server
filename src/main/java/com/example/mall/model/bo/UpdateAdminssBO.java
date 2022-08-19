package com.example.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UpdateAdminssBO
 * @Description
 * @Date 2022-08-19 22:52
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateAdminssBO {

    private Integer id;
    private String nickname;
    private String email;
    private String pwd;
}
