package com.example.mall.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class ApplicationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //如果前端页面和服务器系统不在一个主机、端口号，那么就是跨域
        //跨域的时候，请求需要得到服务器的授权：四个响应头
        //每次请求都会发送OPTIONS请求来获取到授权信息，这部分代码写到filter中
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //哪些来源的主机端口号可以发送请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        //可以发往哪些请求的方法
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,PUT,DELETE");
        //发送请求时允许携带的头有哪些
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,Authorization,Content-Type");
        //是否可以携带cookie的凭证（目前还不会携带cookie）
        response.setHeader("Access-Control-Allow-Credentials","true");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
