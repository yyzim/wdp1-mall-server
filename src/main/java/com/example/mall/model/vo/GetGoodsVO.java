package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname GetGoodsVO
 * @Description
 * @Date 2022-08-24 21:39
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class GetGoodsVO {

    private Integer code;
    private DataDTO data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private Integer total;
        private List<GoodsDTO> goods;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class GoodsDTO {
            private Integer id;
            private String img;
            private String name;
            private Double price;
            private Integer typeId;
            private Integer stockNum;
        }
    }
}
