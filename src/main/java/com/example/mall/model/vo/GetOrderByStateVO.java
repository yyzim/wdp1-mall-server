package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname GetOrderByStateVO
 * @Description
 * @Date 2022-08-22 10:45
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class GetOrderByStateVO {

    private Integer code;
    private List<DataDTO> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private Integer id;
        private Integer state;
        private Integer goodsNum;
        private Double amount;
        private Integer goodsDetailId;
        private String createtime;
        private Boolean hasComment;
        private GoodsDTO goods;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class GoodsDTO {
            private Integer id;
            private String img;
            private String name;
            private Integer goodsDetailId;
            private String spec;
            private Double unitPrice;
        }
    }
}
