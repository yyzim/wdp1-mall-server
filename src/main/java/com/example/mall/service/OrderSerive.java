package com.example.mall.service;

import com.example.mall.model.bo.ChangeOrderBO;
import com.example.mall.model.bo.OrdersByPageBO;
import com.example.mall.model.vo.ChangeOrderVO;
import com.example.mall.model.vo.DeleteOrderVO;
import com.example.mall.model.vo.OrderVO;
import com.example.mall.model.vo.OrdersByPageVO;

/**
 * @Classname OrderSerive
 * @Description
 * @Date 2022-08-21 14:16
 * @Created by Yang Yi-zhou
 */
public interface OrderSerive {
    OrdersByPageVO ordersByPage(OrdersByPageBO ordersByPageBO);

    OrderVO order(Integer id);

    ChangeOrderVO changeOrder(ChangeOrderBO changeOrderBO);

    DeleteOrderVO deleteOrder(Integer id);
}
