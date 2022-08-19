package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname AdminLoginResultVO
 * @Description
 * @Date 2022-08-19 16:59
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminLoginResultVO {

    private Integer code;
    private DataDTO data;
    private String message;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private String token;
        private String name;
    }
}
