package com.example.mall.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname SearchGoodsBO
 * @Description
 * @Date 2022-08-24 23:44
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class SearchGoodsBO {

    private String keyword;
    private Integer pageSize;
    private Integer currentPage;
}
