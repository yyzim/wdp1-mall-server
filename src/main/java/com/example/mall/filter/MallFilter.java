package com.example.mall.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname MallFilter
 * @Description
 * @Date 2022-08-21 21:06
 * @Created by Yang Yi-zhou
 */
@WebFilter("/api/mall/*")
public class MallFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //表示哪些主机可以访问当前的系统
        response.setHeader("Access-Control-AllowOrigin", "http://localhost:8085");
        response.setHeader("Access-Control-AllowMethods", "POST,GET,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "x-requestedwith,Authorization,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");



        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
