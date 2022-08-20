package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname SearchUserVO
 * @Description
 * @Date 2022-08-20 10:18
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchUserVO {

    private Integer code;
    private List<DataVO> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataVO {
        private Integer id;
        private String email;
        private String nickname;
        private String pwd;
        private String recipient;
        private String address;
        private String phone;
    }
}
