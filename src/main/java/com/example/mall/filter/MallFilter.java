package com.example.mall.filter;

import com.example.mall.model.vo.FilterVO;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //如果前端页面和服务器系统不在一个主机、端口号，那么就是跨域
        //跨域的时候，请求需要得到服务器的授权：四个响应头
        //每次请求都会发送OPTIONS请求来获取到授权信息，这部分代码写到filter中
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //哪些来源的主机端口号可以发送请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8085");

        //可以发往哪些请求的方法
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE");
        //发送请求时允许携带的头有哪些
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type");
        //是否可以携带cookie的凭证（目前还不会携带cookie）
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setContentType("text/html;charset=utf-8");

        //获取当前的uri /api/admin/admin/login
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

//        if (!"OPTIONS".equals(method)) {
//
//            if (StringUtils.contains(requestURI, "/api/mall/user/")) {
//                if (!("/api/mall/user/login".equals(requestURI)
//                        || "/api/mall/user/signup".equals(requestURI))) {
//                    //做一个权限控制，看当前已经登陆
//                    HttpSession session = request.getSession();
//                    Object user =  session.getAttribute("user");
//                    //判断是否已经登陆
//                    if (user == null) {
//                        //未登录
//                        response.setContentType("text/html;charset=utf-8");
////                        response.getWriter().println("没有权限访问，请先登陆");
//
//                        FilterVO filterVO = new FilterVO();
//                        filterVO.setCode(10000);
//                        filterVO.setMessage("没有权限访问，请先登陆");
//                        response.getWriter().println(new Gson().toJson(filterVO));
//                        return;
//                    }
//                }
//            }
//
//        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
