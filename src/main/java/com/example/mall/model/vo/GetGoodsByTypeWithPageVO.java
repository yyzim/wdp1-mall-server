package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname GetGoodsByTypeWithPageVO
 * @Description
 * @Date 2022-08-24 23:04
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class GetGoodsByTypeWithPageVO {

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
