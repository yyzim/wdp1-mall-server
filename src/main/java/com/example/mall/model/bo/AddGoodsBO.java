package com.example.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname AddGoodsBO
 * @Description
 * @Date 2022-08-20 14:57
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddGoodsBO {

    private String name;
    private String typeId;
    private String img;
    private String desc;
    private List<SpecListDTO> specList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SpecListDTO {
        private String specName;
        private String stockNum;
        private String unitPrice;
    }
}
