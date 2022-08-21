package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname NoReplyMsgVO
 * @Description
 * @Date 2022-08-21 18:23
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class NoReplyMsgVO {

    private Integer code;
    private List<DataDTO> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        //留言的id
        private Integer id;
        private Integer userId;
        private Integer goodsId;
        //留言的内容
        private String content;
        //留言的状态 1未回复 / 0已回复
        private Integer state;
        //留言创建的时间
        private String createtime;
        //商品的信息
        private GoodsDTO goods;
        //用户的信息
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
