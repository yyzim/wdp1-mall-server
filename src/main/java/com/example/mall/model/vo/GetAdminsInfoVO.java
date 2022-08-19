package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname GetAdminsInfoVO
 * @Description
 * @Date 2022-08-19 22:34
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class GetAdminsInfoVO {

    private Integer code;
    private DataVO data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataVO {
        private Integer id;
        private String email;
        private String nickname;
        private String pwd;
    }
}
