package com.example.mall.controller;


import com.example.mall.model.bo.MallLoginBO;
import com.example.mall.model.bo.MallSignupBO;
import com.example.mall.model.vo.MallLoginVO;
import com.example.mall.model.vo.MallSignupVO;
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

@WebServlet("/api/mall/user/*")
public class MallUserServlet extends HttpServlet {
    private Gson gson = new Gson();
    private MallUserService mallUserService = new MallUserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("login".equals(targetResource)) {
            login(req, resp);
        } else if ("signup".equals(targetResource)) {
            signup(req, resp);
        }
    }

    private void signup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        MallSignupBO mallSignupBO = ParseUtils.parseToBO(req, MallSignupBO.class);
        //service
        MallSignupVO mallSignupVO = mallUserService.signup(req, mallSignupBO);
        //响应
        resp.getWriter().println(gson.toJson(mallSignupVO));
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        MallLoginBO mallLoginBO = ParseUtils.parseToBO(req, MallLoginBO.class);
        //service
        MallLoginVO mallLoginVO = mallUserService.login(req, mallLoginBO);
        //响应
        resp.getWriter().println(gson.toJson(mallLoginVO));
    }
}
