package com.example.mall.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname GetGoodsByTypeBO
 * @Description
 * @Date 2022-08-24 23:04
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class GetGoodsByTypeBO {

    private String typeId;
    private Integer pageSize;
    private Integer currentPage;
}
