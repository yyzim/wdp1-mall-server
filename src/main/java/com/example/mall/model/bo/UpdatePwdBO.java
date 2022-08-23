package com.example.mall.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UpdatePwdBO
 * @Description
 * @Date 2022-08-23 11:49
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class UpdatePwdBO {

    private Integer id;
    private String oldPwd;
    private String newPwd;
    private String confirmPwd;
}
