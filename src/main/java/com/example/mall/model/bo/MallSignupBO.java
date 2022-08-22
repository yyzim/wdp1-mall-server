package com.example.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MallSignupBO
 * @Description
 * @Date 2022-08-22 9:18
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MallSignupBO {

    private String email;
    private String nickname;
    private String pwd;
    private String recipient;
    private String address;
    private String phone;
}
