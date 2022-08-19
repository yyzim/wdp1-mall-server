package com.example.mall.controller;

import com.example.mall.mapper.AdminMapper;
import com.example.mall.model.bo.AddAdminssBO;
import com.example.mall.model.bo.AdminLoginBO;
import com.example.mall.model.bo.SearchAdminsBO;
import com.example.mall.model.bo.UpdateAdminssBO;
import com.example.mall.model.vo.*;
import com.example.mall.service.AdminService;
import com.example.mall.service.AdminServiceImpl;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Classname AdminServlet
 * @Description
 * @Date 2022-08-19 16:10
 * @Created by Yang Yi-zhou
 */
@WebServlet("/api/admin/admin/*")
public class AdminServlet extends HttpServlet {
    private Gson gson = new Gson();
    private AdminService adminService = new AdminServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发请求
        //获取ContextPath
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        //解析请求资源名称
        String targetResource = req.getRequestURI().replace(contextPath + servletPath + "/", "");
        if ("login".equals(targetResource)) {
            login(req, resp);
        } else if ("getSearchAdmins".equals(targetResource)) {
            getSearchAdmins(req, resp);
        } else if ("addAdminss".equals(targetResource)) {
            addAdminss(req, resp);
        } else if ("updateAdminss".equals(targetResource)) {
            updateAdminss(req, resp);
        }
    }

    private void updateAdminss(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷到BO
        UpdateAdminssBO updateAdminssBO = getBO(req, UpdateAdminssBO.class);
        //更新数据库
        UpdateAdminssVO updateAdminssVO = adminService.updateAdminss(updateAdminssBO);
        //写入响应体返回
        resp.getWriter().println(gson.toJson(updateAdminssVO));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发请求
        //获取ContextPath
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        //解析请求资源名称
        String targetResource = req.getRequestURI().replace(contextPath + servletPath + "/", "");
        if ("allAdmins".equals(targetResource)) {
            allAdmins(req, resp);
        } else if ("deleteAdmins".equals(targetResource)) {
            deleteAdmins(req, resp);
        } else if ("getAdminsInfo".equals(targetResource)) {
            getAdminsInfo(req, resp);
        }
    }

    private void getAdminsInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数获取id
        Integer id = Integer.parseInt(req.getParameter("id"));
        //根据id去数据库查询数据
        GetAdminsInfoVO adminsInfoVO = adminService.getAdminsInfo(id);
        //写入响应返回
        resp.getWriter().println(gson.toJson(adminsInfoVO));

    }

    private void deleteAdmins(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析参数获取id
        Integer id = Integer.parseInt(req.getParameter("id"));
        //根据id去数据库删除数据
        Boolean isDeleteSuccess = adminService.deleteAdmins(id);
        DeleteAdminsVO deleteAdminsVO = new DeleteAdminsVO(isDeleteSuccess ? 0 : 1000);
        //返回响应
        resp.getWriter().println(gson.toJson(deleteAdminsVO));
    }


    private void addAdminss(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析请求体到BO
        AddAdminssBO addAdminssBO = getBO(req, AddAdminssBO.class);
        //把BO添加到数据库中
        AddAdminssVO addAdminssVO = adminService.addAdminss(addAdminssBO);
        //把VO写入响应体返回
        resp.getWriter().println(gson.toJson(addAdminssVO));
    }

    private void getSearchAdmins(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析请求体到BO
        SearchAdminsBO searchAdminsBO = getBO(req, SearchAdminsBO.class);
        //根据数据去数据库查询，封装到VO中返回
        SearchAdminsVO searchAdminsVO = adminService.searchAdmins(searchAdminsBO);
        //把VO写入响应体返回
        resp.getWriter().println(gson.toJson(searchAdminsVO));
    }

    private void allAdmins(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取结果
        AllAdminsVO allAdminsVO = adminService.getAllAdmins();
        //写入响应体
        resp.getWriter().println(gson.toJson(allAdminsVO));

    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AdminLoginBO adminLoginBO = getBO(req, AdminLoginBO.class);
        String email = adminLoginBO.getEmail();
        String pwd = adminLoginBO.getPwd();
        //返回对象
        AdminLoginResultVO adminLoginResultVO = new AdminLoginResultVO();
        //验证登陆信息
        if (StringUtils.isBlank(email) || StringUtils.isBlank(pwd)) {
            //如果邮箱或者密码为空
            adminLoginResultVO.setCode(10000);
            adminLoginResultVO.setMessage("账户或密码为空，请重新输入");
            resp.getWriter().println(gson.toJson(adminLoginResultVO));
            return;
        }
        //不为空，与数据库信息进行比对验证
        int code = adminService.login(adminLoginBO);
        if (code == 200) {
            //登陆成功
            adminLoginResultVO.setCode(0);
            String name = adminService.getAdminName(email);
            adminLoginResultVO.setData(new AdminLoginResultVO.DataDTO(name, name));
        } else if (code == 404) {
            //登陆失败
            adminLoginResultVO.setCode(1000);
            adminLoginResultVO.setMessage("账户或密码错误");
        }
        //转换成json写入响应体
        resp.getWriter().println(gson.toJson(adminLoginResultVO));

    }

    /**
     * 解析请求体到T对象
     *
     * @param req   request
     * @param clazz T对象的反射
     * @param <T>   泛型方法
     * @return T对象
     */
    private <T> T getBO(HttpServletRequest req, Class<T> clazz) throws IOException {
        //获取请求中json参数封装到AdminLoginBO中
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
        T obj = gson.fromJson(requestBody, clazz);
        return obj;
    }


}
