package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname MallSearchGoodsVO
 * @Description
 * @Date 2022-08-22 10:23
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class MallSearchGoodsVO {

    private Integer code;
    private Integer message;
    private List<DataDTO> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private Integer id;
        private String img;
        private String name;
        private Double price;
        private Integer typeId;
    }
}
