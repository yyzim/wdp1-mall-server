package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname OrderVo
 * @Description
 * @Date 2022-08-21 16:58
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class OrderVO {

    private Integer code;
    private DataDTO data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private Integer id;
        private Double amount;
        private Integer num;
        private Integer goodsDetailId;
        private Integer state;
        private String goods;
        private List<SpecDTO> spec;
        private List<StatesDTO> states;
        private CurStateDTO curState;
        private CurSpecDTO curSpec;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class CurStateDTO {
            private Integer id;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class CurSpecDTO {
            private Integer id;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class SpecDTO {
            private Integer id;
            private String specName;
            private Double unitPrice;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class StatesDTO {
            private Integer id;
            private String name;
        }
    }
}
