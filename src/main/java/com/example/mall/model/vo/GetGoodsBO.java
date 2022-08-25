package com.example.mall.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname GetGoodsBO
 * @Description
 * @Date 2022-08-24 21:39
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class GetGoodsBO {


    private Integer id;
    private Integer currentPage;
    private Integer pagesize;
}
