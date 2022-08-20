package com.example.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname ChangePwdBO
 * @Description
 * @Date 2022-08-20 9:12
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangePwdBO {

    private String adminToken;
    private String oldPwd;
    private String newPwd;
    private String confirmPwd;
}
