package com.example.mall.controller;

import com.example.mall.model.bo.AddGoodsBO;
import com.example.mall.model.bo.AddTypeBO;
import com.example.mall.model.bo.UpdateGoodsBO;
import com.example.mall.model.vo.*;
import com.example.mall.service.GoodsService;
import com.example.mall.service.GoodsServiceImpl;
import com.example.mall.util.ParseUtils;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileUploadException;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
    }

}
