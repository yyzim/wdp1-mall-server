package com.example.mall.controller;

import com.example.mall.model.bo.*;
import com.example.mall.model.vo.*;
import com.example.mall.service.GoodsService;
import com.example.mall.service.GoodsServiceImpl;
import com.example.mall.service.UserService;
import com.example.mall.service.UserServiceImpl;
import com.example.mall.util.ParseUtils;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @Classname BusinessServlet
 * @Description 负责后台业务模块
 * @Date 2022-08-20 10:36
 * @Created by Yang Yi-zhou
 */
@WebServlet("/api/admin/goods/*")
public class GoodsServlet extends HttpServlet {
    private Gson gson = new Gson();
    //goods业务层
    private GoodsService goodsService = new GoodsServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        String targetResource = req.getRequestURI().replace(contextPath + servletPath + "/", "");
        if ("getType".equals(targetResource)) {
            getType(req, resp);
        } else if ("getGoodsByType".equals(targetResource)) {
            getGoodsByType(req, resp);
        } else if ("getGoodsInfo".equals(targetResource)) {
            getGoodsInfo(req, resp);
        } else if ("deleteGoods".equals(targetResource)) {
            deleteGoods(req, resp);
        } else if ("noReplyMsg".equals(targetResource)) {
            noReplyMsg(req, resp);
        } else if ("repliedMsg".equals(targetResource)) {
            repliedMsg(req, resp);
        } else if ("deleteType".equals(targetResource)) {
            deleteType(req, resp);
        } else if ("getGoodsByTypeAndPage".equals(targetResource)) {
            getGoodsByTypeAndPage(req, resp);
        }

    }

    private void getGoodsByTypeAndPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer typeId = Integer.parseInt(req.getParameter("typeId"));
        Integer page = Integer.parseInt(req.getParameter("page"));
        Integer size = Integer.parseInt(req.getParameter("size"));
        //根据typeId去数据库取出数据
        GetGoodsByTypeVO goodsByType = goodsService.getGoodsByTypeAndPage(typeId, page, size);
        //响应
        resp.getWriter().println(gson.toJson(goodsByType));

    }

    private void deleteType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer typeId = Integer.parseInt(req.getParameter("typeId"));
        DeleteTypeVO deleteTypeVO = goodsService.deleteType(typeId);
        resp.getWriter().println(gson.toJson(deleteTypeVO));
    }

    private void repliedMsg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //service
        RepliedMsgVO repliedMsgVO = goodsService.repliedMsg();
        //响应
        resp.getWriter().println(gson.toJson(repliedMsgVO));
    }

    private void noReplyMsg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //service
        NoReplyMsgVO noReplyMsgVO = goodsService.noReplyMsg();
        //响应
        resp.getWriter().println(gson.toJson(noReplyMsgVO));
    }

    private void deleteGoods(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //取出id
        int id = Integer.parseInt(req.getParameter("id"));
        //根据typeId去数据库取出数据
        DeleteGoodsVO deleteGoodsVO = goodsService.deleteGoods(id);
        //响应
        resp.getWriter().println(gson.toJson(deleteGoodsVO));
    }

    private void getGoodsInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //取出id
        int id = Integer.parseInt(req.getParameter("id"));
        //根据typeId去数据库取出数据
        GetGoodsInfoVO getGoodsInfoVO = goodsService.getGoodsInfo(id);
        //响应
        resp.getWriter().println(gson.toJson(getGoodsInfoVO));
    }

    private void getGoodsByType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //取出TypeId
        int typeId = Integer.parseInt(req.getParameter("typeId"));
        //根据typeId去数据库取出数据
        GetGoodsByTypeVO goodsByType = goodsService.getGoodsByType(typeId);
        //响应
        resp.getWriter().println(gson.toJson(goodsByType));
    }

    private void getType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取商品类型
        GetTypeVO typeVO = goodsService.getType();
        //响应
        resp.getWriter().println(gson.toJson(typeVO));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分发
        String targetResource = ParseUtils.parseURIToTargetResource(req);
        if ("addType".equals(targetResource)) {
            addType(req, resp);
        } else if ("imgUpload".equals(targetResource)) {
            try {
                imgUpload(req, resp);
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            }
        } else if ("addGoods".equals(targetResource)) {
            addGoods(req, resp);
        } else if ("updateGoods".equals(targetResource)) {
            updateGoods(req, resp);
        } else if ("reply".equals(targetResource)) {
            reply(req, resp);
        } else if ("addSpec".equals(targetResource)) {
            addSpec(req, resp);
        } else if ("deleteSpec".equals(targetResource)) {
            deleteSpec(req, resp);
        } else if ("getGoods".equals(targetResource)) {
            getGoods(req, resp);
        }
    }

    private void getGoods(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GetGoodsBO getGoodsBO = ParseUtils.parseToBO(req, GetGoodsBO.class);
        GetGoodsVO getGoodsVO = goodsService.getGoods(getGoodsBO);
        resp.getWriter().println(gson.toJson(getGoodsVO));
    }

    private void deleteSpec(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GeneralVO generalVO = new GeneralVO();
        generalVO.setCode(0);
        resp.getWriter().println(gson.toJson(generalVO));
    }

    private void addSpec(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AddSpecBO addSpecBO = ParseUtils.parseToBO(req, AddSpecBO.class);
        AddSpecVO addSpecVO = goodsService.addSpec(addSpecBO);
        resp.getWriter().println(gson.toJson(addSpecVO));
    }

    private void reply(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        ReplyBO replyBO = ParseUtils.parseToBO(req, ReplyBO.class);
        //service
        ReplyVO replyVO = goodsService.reply(replyBO);
        //响应
        resp.getWriter().println(gson.toJson(replyVO));
    }

    private void updateGoods(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        UpdateGoodsBO updateGoodsBO = ParseUtils.parseToBO(req, UpdateGoodsBO.class);
        //service
        UpdateGoodsVO updateGoodsVO = goodsService.updateGoods(updateGoodsBO);
        //响应
        resp.getWriter().println(gson.toJson(updateGoodsVO));
    }

    private void addGoods(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        AddGoodsBO addGoodsBO = ParseUtils.parseToBO(req, AddGoodsBO.class);
        //交给service处理
        String serverHost = (String) req.getServletContext().getAttribute("server_host");
        AddGoodsVO addGoodsVO = goodsService.addGoods(serverHost, addGoodsBO);
        //响应
        resp.getWriter().println(gson.toJson(addGoodsVO));
    }

    private void imgUpload(HttpServletRequest req, HttpServletResponse resp) throws IOException, FileUploadException {
        ImgUploadVO imgUploadVO = goodsService.imgUpload(req, resp);
        //响应
        resp.getWriter().println(gson.toJson(imgUploadVO));
    }

    private void addType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析载荷
        AddTypeBO addTypeBO = ParseUtils.parseToBO(req, AddTypeBO.class);
        //添加进数据库
        AddTypeVO addTypeVO = goodsService.addType(addTypeBO);
        //响应
        resp.getWriter().println(gson.toJson(addTypeVO));

    }
}
