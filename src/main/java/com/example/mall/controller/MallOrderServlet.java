package com.example.mall.controller;


import com.example.mall.model.bo.AddOrderBO;
import com.example.mall.model.bo.SendCommentBO;
import com.example.mall.model.bo.SettleAccountsBO;
import com.example.mall.model.vo.*;
import com.example.mall.service.MallGoodsService;
import com.example.mall.service.MallGoodsServiceImpl;
import com.example.mall.service.MallOrderService;
import com.example.mall.service.MallOrderServiceImpl;
import com.example.mall.util.ParseUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname MallUserServlet
 * @Description
 * @Date 2022-08-21 21:17
 * @Created by Yang Yi-zhou
 */

@WebServlet("/api/mall/order/*")
public class MallOrderServlet extends HttpServlet {
    private Gson gson = new Gson();
    private MallOrderService mallOrderService = new MallOrderServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("settleAccounts".equals(targetResource)) {
            settleAccounts(req, resp);
        } else if ("addOrder".equals(targetResource)) {
            addOrder(req, resp);
        } else if ("sendComment".equals(targetResource)) {
            sendComment(req, resp);
        }
    }

    private void sendComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        SendCommentBO sendCommentBO = ParseUtils.parseToBO(req, SendCommentBO.class);
        //service
        SendCommentVO sendCommentVO = mallOrderService.sendComment(sendCommentBO);
        //响应
        resp.getWriter().println(gson.toJson(sendCommentVO));
    }

    private void addOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        AddOrderBO addOrderBO = ParseUtils.parseToBO(req, AddOrderBO.class);
        //service
        AddOrderVO addOrderVO = mallOrderService.addOrder(addOrderBO);
        //响应
        resp.getWriter().println(gson.toJson(addOrderVO));
    }

    private void settleAccounts(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        SettleAccountsBO settleAccountsBO = ParseUtils.parseToBO(req, SettleAccountsBO.class);
        //service
        SettleAccountsVO settleAccountsVO = mallOrderService.settleAccounts(req, settleAccountsBO);
        //响应
        resp.getWriter().println(gson.toJson(settleAccountsVO));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("getOrderByState".equals(targetResource)) {
            getOrderByState(req, resp);
        } else if ("pay".equals(targetResource)) {
            pay(req, resp);
        } else if ("confirmReceive".equals(targetResource)) {
            confirmReceive(req, resp);
        } else if ("deleteOrder".equals(targetResource)) {
            deleteOrder(req, resp);
        }


    }

    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数
        Integer id = Integer.parseInt(req.getParameter("id"));
        //service
        DeleteOrderVO deleteOrderVO = mallOrderService.deleteOrder(id);
        //响应
        resp.getWriter().println(gson.toJson(deleteOrderVO));
    }

    private void confirmReceive(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数
        Integer id = Integer.parseInt(req.getParameter("id"));
        //service
        ConfirmReceiveVO confirmReceiveVO = mallOrderService.confirmReceive(id);
        //响应
        resp.getWriter().println(gson.toJson(confirmReceiveVO));
    }

    private void pay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数
        Integer id = Integer.parseInt(req.getParameter("id"));
        //service
        PayVO payVO = mallOrderService.pay(id);
        //响应
        resp.getWriter().println(gson.toJson(payVO));
    }

    private void getOrderByState(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取参数
        int state = Integer.parseInt(req.getParameter("state"));
        String token = req.getParameter("token");
        //service
        GetOrderByStateVO getOrderByStateVO = mallOrderService.getOrderByState(state, token);
        //响应
        resp.getWriter().println(gson.toJson(getOrderByStateVO));
    }
}
