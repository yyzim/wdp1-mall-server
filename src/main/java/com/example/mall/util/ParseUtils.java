package com.example.mall.util;

import com.google.gson.Gson;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Classname ParseUtils
 * @Description
 * @Date 2022-08-20 12:03
 * @Created by Yang Yi-zhou
 */
public class ParseUtils {
    public static String parseURIToTargetResource(HttpServletRequest req){
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        String targetResource = req.getRequestURI().replace(contextPath + servletPath + "/", "");
        return targetResource;
    }

    public static <T> T parseToBO(HttpServletRequest req, Class<T> clazz) throws IOException {
        //获取请求体参数
        ServletInputStream inputStream = req.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        //拿到请求体
        String requestBody = outputStream.toString("utf-8");
        //关闭资源
        inputStream.close();
        outputStream.close();
        //解析请求体
        Gson gson = new Gson();
        T obj = gson.fromJson(requestBody, clazz);
        return obj;
    }
}
