package com.example.mall.service;

import com.example.mall.model.bo.AddOrderBO;
import com.example.mall.model.bo.SendCommentBO;
import com.example.mall.model.bo.SettleAccountsBO;
import com.example.mall.model.vo.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname MallOrderService
 * @Description
 * @Date 2022-08-22 10:44
 * @Created by Yang Yi-zhou
 */
public interface MallOrderService {
    GetOrderByStateVO getOrderByState(int state, String token);

    SettleAccountsVO settleAccounts(HttpServletRequest req,SettleAccountsBO settleAccountsBO);

    AddOrderVO addOrder(AddOrderBO addOrderBO);

    PayVO pay(Integer orderId);

    ConfirmReceiveVO confirmReceive(Integer orderId);

    SendCommentVO sendComment(SendCommentBO sendCommentBO);

    DeleteOrderVO deleteOrder(Integer orderId);
}
