package com.example.mall.service;

import com.example.mall.model.bo.*;
import com.example.mall.model.vo.*;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname GoodsService
 * @Description
 * @Date 2022-08-20 10:43
 * @Created by Yang Yi-zhou
 */
public interface GoodsService {

    GetTypeVO getType();

    GetGoodsByTypeVO getGoodsByType(int typeId);

    AddTypeVO addType(AddTypeBO addTypeBO);

    ImgUploadVO imgUpload(HttpServletRequest req, HttpServletResponse resp) throws IOException, FileUploadException;

    AddGoodsVO addGoods(AddGoodsBO addGoodsBO);

    UpdateGoodsVO updateGoods(UpdateGoodsBO updateGoodsBO);

    GetGoodsInfoVO getGoodsInfo(int id);

    DeleteGoodsVO deleteGoods(int id);

    NoReplyMsgVO noReplyMsg();

    RepliedMsgVO repliedMsg();

    ReplyVO reply(ReplyBO replyBO);

    AddSpecVO addSpec(AddSpecBO addSpecBO);
}
