package com.example.mall.controller;


import com.example.mall.model.bo.MallLoginBO;
import com.example.mall.model.bo.MallSignupBO;
import com.example.mall.model.vo.MallGetTypeVO;
import com.example.mall.model.vo.MallLoginVO;
import com.example.mall.model.vo.MallSignupVO;
import com.example.mall.service.MallIndexService;
import com.example.mall.service.MallIndexServiceImpl;
import com.example.mall.service.MallUserService;
import com.example.mall.service.MallUserServiceImpl;
import com.example.mall.util.ParseUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Classname MallUserServlet
 * @Description
 * @Date 2022-08-21 21:17
 * @Created by Yang Yi-zhou
 */

@WebServlet("/api/mall/index/*")
public class MallIndexServlet extends HttpServlet {
    private Gson gson = new Gson();
    private MallIndexService mallIndexService = new MallIndexServiceImpl();

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
        if ("getType".equals(targetResource)) {
            getType(req, resp);
        }


    }

    private void getType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //service
        MallGetTypeVO mallGetTypeVO = mallIndexService.getType();
        //响应
        resp.getWriter().println(gson.toJson(mallGetTypeVO));
    }
}
