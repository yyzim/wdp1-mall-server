package com.example.mall.controller;

import com.example.mall.model.bo.ChangeOrderBO;
import com.example.mall.model.bo.OrdersByPageBO;
import com.example.mall.model.vo.*;
import com.example.mall.service.OrderSerive;
import com.example.mall.service.OrderServiceImpl;
import com.example.mall.util.ParseUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname BusinessServlet
 * @Description 负责后台业务模块
 * @Date 2022-08-20 10:36
 * @Created by Yang Yi-zhou
 */
@WebServlet("/api/admin/order/*")
public class OrderServlet extends HttpServlet {
    private Gson gson = new Gson();
    //order service
    OrderSerive orderSerive = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("order".equals(targetResource)) {
            order(req, resp);
        } else if ("deleteOrder".equals(targetResource)) {
            deleteOrder(req, resp);
        }

    }

    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析获取参数id
        Integer id = Integer.parseInt(req.getParameter("id"));
        //service
        DeleteOrderVO deleteOrderVO = orderSerive.deleteOrder(id);
        //响应
        resp.getWriter().println(gson.toJson(deleteOrderVO));
    }

    private void order(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析获取参数id
        Integer id = Integer.parseInt(req.getParameter("id"));
        //service
        OrderVO orderVO = orderSerive.order(id);
        //响应
        resp.getWriter().println(gson.toJson(orderVO));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("ordersByPage".equals(targetResource)) {
            ordersByPage(req, resp);
        } else if ("changeOrder".equals(targetResource)) {
            changeOrder(req, resp);
        }
    }

    private void changeOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        ChangeOrderBO changeOrderBO = ParseUtils.parseToBO(req, ChangeOrderBO.class);
        //service
        ChangeOrderVO changeOrderVO = orderSerive.changeOrder(changeOrderBO);
        //响应
        resp.getWriter().println(gson.toJson(changeOrderVO));
    }

    private void ordersByPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        OrdersByPageBO ordersByPageBO = ParseUtils.parseToBO(req, OrdersByPageBO.class);
        //service
        OrdersByPageVO ordersByPageVO = orderSerive.ordersByPage(ordersByPageBO);
        //响应
        resp.getWriter().println(gson.toJson(ordersByPageVO));

    }

}
