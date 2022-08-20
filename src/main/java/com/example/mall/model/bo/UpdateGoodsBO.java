package com.example.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname UpdateGoodsBO
 * @Description
 * @Date 2022-08-20 16:48
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateGoodsBO {

    private String id;
    private String name;
    private Integer typeId;
    private String img;
    private String desc;
    private List<SpecListDTO> specList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SpecListDTO {
        private Integer id;
        private String specName;
        private Integer stockNum;
        private String unitPrice;
    }
}
