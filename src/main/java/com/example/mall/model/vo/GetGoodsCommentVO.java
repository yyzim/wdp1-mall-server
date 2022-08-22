package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname GetGoodsCommentVO
 * @Description
 * @Date 2022-08-22 11:49
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class GetGoodsCommentVO {


    private Integer code;
    private DataDTO data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private List<CommentListDTO> commentList;
        private Double rate;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class CommentListDTO {
            private UserDTO user;
            private Double score;
            private Integer id;
            private String specName;
            private String comment;
            private String time;
            private Integer userId;

            @NoArgsConstructor
            @AllArgsConstructor
            @Data
            public static class UserDTO {
                private String nickname;
            }
        }
    }
}
