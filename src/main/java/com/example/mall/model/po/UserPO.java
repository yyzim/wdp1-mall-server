package com.example.mall.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UserPO
 * @Description
 * @Date 2022-08-20 9:58
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPO {

    private Integer id;
    private String email;
    private String nickname;
    private String pwd;
    private String recipient;
    private String address;
    private String phone;
}
