package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname GetGoodsByTypeVO
 * @Description
 * @Date 2022-08-20 11:07
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetGoodsByTypeVO {

    private Integer code;
    private List<DataDTO> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataDTO {
        private Integer id;
        private String img;
        private String name;
        private Double price;
        private Integer typeId;
        private Integer stockNum;
    }
}
