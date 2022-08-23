package com.example.mall.controller;


import com.example.mall.model.bo.MallLoginBO;
import com.example.mall.model.bo.MallSignupBO;
import com.example.mall.model.bo.UpdatePwdBO;
import com.example.mall.model.bo.UpdateUserDataBO;
import com.example.mall.model.po.UserPO;
import com.example.mall.model.vo.*;
import com.example.mall.service.MallUserService;
import com.example.mall.service.MallUserServiceImpl;
import com.example.mall.util.ParseUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

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
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("data".equals(targetResource)) {
            data(req, resp);
        }
    }

    private void data(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取参数token=admin
        String token = req.getParameter("token");
        UserPO user = (UserPO) req.getSession().getAttribute("user");
        DataVO dataVO = new DataVO();
        if (user != null) {
            DataVO.DataDTO dataDTO = new DataVO.DataDTO(0, user.getId(), user.getEmail(), user.getNickname(), user.getRecipient(), user.getAddress(), user.getPhone());

            dataVO.setCode(0);
            dataVO.setData(dataDTO);
        }

        resp.getWriter().println(gson.toJson(dataVO));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("login".equals(targetResource)) {
            login(req, resp);
        } else if ("signup".equals(targetResource)) {
            signup(req, resp);
        } else if ("updateUserData".equals(targetResource)) {
            updateUserData(req, resp);
        } else if ("updatePwd".equals(targetResource)) {
            updatePwd(req, resp);
        }
    }

    private void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UpdatePwdBO updatePwdBO = ParseUtils.parseToBO(req, UpdatePwdBO.class);
        //简单的验证
        UserPO user = (UserPO) req.getSession().getAttribute("user");

        if (!StringUtils.equals(user.getPwd(), updatePwdBO.getOldPwd())) {
            resp.getWriter().println(gson.toJson(new UpdatePwdVO(0, "密码验证失败")));
            return;
        }


        UpdatePwdVO updatePwdVO = mallUserService.updatePwd(updatePwdBO);
        resp.getWriter().println(gson.toJson(updatePwdVO));
    }

    private void updateUserData(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UpdateUserDataBO updateUserDataBO = ParseUtils.parseToBO(req, UpdateUserDataBO.class);
        UpdateUserDataVO updateUserDataVO = mallUserService.updateUserData(updateUserDataBO);
        resp.getWriter().println(gson.toJson(updateUserDataVO));

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
