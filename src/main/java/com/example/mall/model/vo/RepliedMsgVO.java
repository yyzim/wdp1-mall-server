package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname RepliedMsgVO
 * @Description
 * @Date 2022-08-21 18:49
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class RepliedMsgVO {

    private Integer code;
    private List<DataDTO> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private Integer id;
        private Integer userId;
        private Integer goodsId;
        private String content;
        private String replyContent;
        private Integer state;
        private String createtime;
        private GoodsDTO goods;
        private UserDTO user;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class GoodsDTO {
            private String name;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class UserDTO {
            private String name;
        }
    }
}
