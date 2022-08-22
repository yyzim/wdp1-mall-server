package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname MallGetTypeVO
 * @Description
 * @Date 2022-08-22 9:37
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class MallGetTypeVO {

    private Integer code;
    private List<DataDTO> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private Integer id;
        private String name;
    }
}
