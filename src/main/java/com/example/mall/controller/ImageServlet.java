package com.example.mall.controller;

import com.example.mall.util.ParseUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Classname ImageServlet
 * @Description
 * @Date 2022-08-20 11:35
 * @Created by Yang Yi-zhou
 */
@WebServlet("/static/image/*")
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpeg;charset=UTF-8");
//        //拿到图片的名称
//        String contextPath = req.getContextPath();
//        String servletPath = req.getServletPath();
//        String targetResource = req.getRequestURI().replace(contextPath + servletPath + "/", "");
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        //拿到图片的真实路径
        String path = this.getServletContext().getRealPath("/static/image/" + targetResource);
//        String path = this.getServletContext().getRealPath("static/image/1.jpg");
        //读取图片
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        int size = fis.available();
        byte[] data = new byte[size];
        fis.read(data);
        fis.close();
        //写入响应体
        ServletOutputStream ops = resp.getOutputStream();
        ops.write(data);
        ops.flush();
        ops.close();

    }
}
