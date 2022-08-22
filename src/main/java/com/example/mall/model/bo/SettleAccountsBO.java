package com.example.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname SettleAccountsBO
 * @Description
 * @Date 2022-08-22 15:05
 * @Created by Yang Yi-zhou
 */
@NoArgsConstructor
@Data
public class SettleAccountsBO {

    private List<CartListDTO> cartList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CartListDTO {
        private Integer id;
        private Integer goodsNum;
        private Double amount;
    }
}
