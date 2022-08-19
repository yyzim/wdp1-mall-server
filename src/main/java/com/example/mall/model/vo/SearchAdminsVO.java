package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname SearchAdminsVO
 * @Description
 * @Date 2022-08-19 21:27
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchAdminsVO {

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
    }
}
