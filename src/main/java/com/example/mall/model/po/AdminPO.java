package com.example.mall.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname AdminPO
 * @Description
 * @Date 2022-08-19 17:55
 * @Created by Yang Yi-zhou
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPO implements Serializable {
    private Integer id;
    private String account;
    private String name;
    private String password;
}
