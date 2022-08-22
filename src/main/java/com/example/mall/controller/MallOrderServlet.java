package com.example.mall.controller;


import com.example.mall.model.vo.GetGoodsByTypeVO;
import com.example.mall.model.vo.GetOrderByStateVO;
import com.example.mall.model.vo.MallSearchGoodsVO;
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
        if ("".equals(targetResource)) {

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("getOrderByState".equals(targetResource)) {
            getOrderByState(req, resp);
        }


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
