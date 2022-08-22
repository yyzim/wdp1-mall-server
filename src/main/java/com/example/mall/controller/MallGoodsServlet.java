package com.example.mall.controller;


import com.example.mall.model.bo.AskGoodsMsgBO;
import com.example.mall.model.vo.*;
import com.example.mall.service.MallGoodsService;
import com.example.mall.service.MallGoodsServiceImpl;
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

@WebServlet("/api/mall/goods/*")
public class MallGoodsServlet extends HttpServlet {
    private Gson gson = new Gson();
    private MallGoodsService mallGoodsService = new MallGoodsServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("askGoodsMsg".equals(targetResource)) {
            askGoodsMsg(req, resp);
        }
    }

    private void askGoodsMsg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        AskGoodsMsgBO askGoodsMsgBO = ParseUtils.parseToBO(req, AskGoodsMsgBO.class);
        //service
        AskGoodsMsgVO askGoodsMsgVO = mallGoodsService.askGoodsMsg(askGoodsMsgBO);
        //响应
        resp.getWriter().println(gson.toJson(askGoodsMsgVO));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("getGoodsByType".equals(targetResource)) {
            getGoodsByType(req, resp);
        } else if ("searchGoods".equals(targetResource)) {
            searchGoods(req, resp);
        } else if ("getGoodsMsg".equals(targetResource)) {
            getGoodsMsg(req, resp);
        } else if ("getGoodsInfo".equals(targetResource)) {
            getGoodsInfo(req, resp);
        } else if ("getGoodsComment".equals(targetResource)) {
            getGoodsComment(req, resp);
        }


    }

    private void getGoodsComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数
        Integer goodsId = Integer.parseInt(req.getParameter("goodsId"));
        //service
        GetGoodsCommentVO getGoodsCommentVO = mallGoodsService.getGoodsComment(goodsId);
        //响应
        resp.getWriter().println(gson.toJson(getGoodsCommentVO));
    }

    private void getGoodsInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数
        int id = Integer.parseInt(req.getParameter("id"));
        //service
        MallGetGoodsInfoVO mallGetGoodsInfoVO = mallGoodsService.getGoodsInfo(id);
        //响应
        resp.getWriter().println(gson.toJson(mallGetGoodsInfoVO));
    }

    private void getGoodsMsg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数
        int id = Integer.parseInt(req.getParameter("id"));
        //service
        GetGoodsMsgVO getGoodsMsgVO = mallGoodsService.getGoodsMsg(id);
        //响应
        resp.getWriter().println(gson.toJson(getGoodsMsgVO));
    }

    private void searchGoods(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数
        String keyword = req.getParameter("keyword");
        //service
        MallSearchGoodsVO mallSearchGoodsVO = mallGoodsService.searchGoods(keyword);
        //响应
        resp.getWriter().println(gson.toJson(mallSearchGoodsVO));
    }

    private void getGoodsByType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数
        Integer typeId = Integer.parseInt(req.getParameter("typeId"));
        //service
        GetGoodsByTypeVO getGoodsByTypeVO = mallGoodsService.getGoodsByType(typeId);
        //响应
        resp.getWriter().println(gson.toJson(getGoodsByTypeVO));
    }
}
