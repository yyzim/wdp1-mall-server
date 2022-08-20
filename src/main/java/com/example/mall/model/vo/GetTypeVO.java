package com.example.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname GetTypeVO
 * @Description
 * @Date 2022-08-20 10:39
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetTypeVO {

    private Integer code;
    private List<DataVO> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataVO {
        private Integer id;
        private String name;
    }
}
