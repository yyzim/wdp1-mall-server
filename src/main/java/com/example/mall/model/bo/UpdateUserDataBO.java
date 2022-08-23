package com.example.mall.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UpdateUserDataBO
 * @Description
 * @Date 2022-08-23 11:32
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class UpdateUserDataBO {


    private Integer id;
    private String nickname;
    private String recipient;
    private String address;
    private String phone;
}
