package com.example.mall.controller;

import com.example.mall.model.vo.AllUserVO;
import com.example.mall.model.vo.DeleteUserVO;
import com.example.mall.model.vo.SearchUserVO;
import com.example.mall.service.UserService;
import com.example.mall.service.UserServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname UserServlet
 * @Description 负责用户模块
 * @Date 2022-08-20 9:44
 * @Created by Yang Yi-zhou
 */
@WebServlet("/api/admin/user/*")
public class UserServlet extends HttpServlet {
    private Gson gson = new Gson();
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发请求
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        String targetResource = req.getRequestURI().replace(contextPath + servletPath + "/", "");
        if ("allUser".equals(targetResource)) {
            allUser(req, resp);
        } else if ("deleteUser".equals(targetResource)) {
            deleteUser(req, resp);
        } else if ("searchUser".equals(targetResource)) {
            searchUser(req, resp);
        }
    }

    private void searchUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数
        String word = req.getParameter("word");
        //根据搜索词去搜索用户
        SearchUserVO searchUserVO = userService.searchUser(word);
        //响应
        resp.getWriter().println(gson.toJson(searchUserVO));
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数
        int id = Integer.parseInt(req.getParameter("id"));
        //根据id去数据库删除数据
        DeleteUserVO deleteUserVO = userService.deleteUser(id);
        //响应
        resp.getWriter().println(gson.toJson(deleteUserVO));
    }

    private void allUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //交给service层取数据
        AllUserVO allUserVO = userService.allUser();
        //写入响应体返回
        resp.getWriter().println(gson.toJson(allUserVO));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
