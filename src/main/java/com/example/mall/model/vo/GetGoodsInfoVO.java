package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname GetGoodsInfoVO
 * @Description
 * @Date 2022-08-21 12:46
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class GetGoodsInfoVO {

    private Integer code;
    private DataDTO data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private List<SpecsDTO> specs;
        private GoodsDTO goods;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class GoodsDTO {
            private Integer id;
            private String img;
            private String name;
            private String desc;
            private Integer typeId;
            private Double unitPrice;
        }

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
