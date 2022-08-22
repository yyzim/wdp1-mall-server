package com.example.mall.service;

import com.example.mall.model.vo.GetOrderByStateVO;

/**
 * @Classname MallOrderService
 * @Description
 * @Date 2022-08-22 10:44
 * @Created by Yang Yi-zhou
 */
public interface MallOrderService {
    GetOrderByStateVO getOrderByState(int state, String token);
}
