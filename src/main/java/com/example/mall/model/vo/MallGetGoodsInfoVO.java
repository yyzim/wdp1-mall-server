package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname MallGetGoodsInfoVO
 * @Description
 * @Date 2022-08-22 11:37
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class MallGetGoodsInfoVO {

    private Integer code;
    private DataDTO data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private String img;
        private String name;
        private String desc;
        private Integer typeId;
        private List<SpecsDTO> specs;
        private Double unitPrice;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class SpecsDTO {
            private Integer id;
            private String specName;
            private Integer stockNum;
            private Double unitPrice;
        }
    }
}
