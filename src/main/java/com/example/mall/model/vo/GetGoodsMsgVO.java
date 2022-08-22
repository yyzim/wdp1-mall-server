package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname GetGoodsMsgVO
 * @Description
 * @Date 2022-08-22 11:27
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class GetGoodsMsgVO {


    private Integer code;
    private List<DataDTO> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private Integer id;
        private String content;
        private String asker;
        private String time;
        private ReplyDTO reply;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class ReplyDTO {
            private String content;
            private String time;
        }
    }
}
