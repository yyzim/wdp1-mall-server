package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname DataVO
 * @Description
 * @Date 2022-08-23 11:24
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataVO {

    private Integer code;
    private DataDTO data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private Integer code;
        private Integer id;
        private String email;
        private String nickname;
        private String recipient;
        private String address;
        private String phone;
    }
}
