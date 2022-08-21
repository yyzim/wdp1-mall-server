package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname OrdersByPageVO
 * @Description
 * @Date 2022-08-21 14:18
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class OrdersByPageVO {

    private Integer code;
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        private Integer total;
        private List<OrdersDTO> orders;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class OrdersDTO {
            private Integer id;
            private Integer userId;
            private Integer goodsDetailId;
            private String goods;
            private String spec;
            private Integer goodsNum;
            private Double amount;
            private Integer stateId;
            private String state;
            private UserDTO user;

            @NoArgsConstructor
            @AllArgsConstructor
            @Data
            public static class UserDTO {
                private String nickname;
                private String name;
                private String address;
                private String phone;
            }
        }
    }
}
