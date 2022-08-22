package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MallSignupVO
 * @Description
 * @Date 2022-08-22 9:18
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class MallSignupVO {

    private Integer code;
    private String message;
    private DataDTO data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private Integer code;
        private String name;
        private String token;
    }
}
