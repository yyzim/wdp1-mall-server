package com.example.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname AdminLoginBO
 * @Description
 * @Date 2022-08-19 16:25
 * @Created by Yang Yi-zhou
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminLoginBO {
    private String email;
    private String pwd;
}
