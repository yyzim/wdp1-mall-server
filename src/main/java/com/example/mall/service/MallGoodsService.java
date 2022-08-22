package com.example.mall.service;

import com.example.mall.model.bo.AskGoodsMsgBO;
import com.example.mall.model.vo.*;

/**
 * @Classname MallGoodsService
 * @Description
 * @Date 2022-08-22 9:52
 * @Created by Yang Yi-zhou
 */
public interface MallGoodsService {
    GetGoodsByTypeVO getGoodsByType(Integer typeId);

    MallSearchGoodsVO searchGoods(String keyword);

    GetGoodsMsgVO getGoodsMsg(Integer id);

    MallGetGoodsInfoVO getGoodsInfo(int id);

    GetGoodsCommentVO getGoodsComment(int goodsId);

    AskGoodsMsgVO askGoodsMsg(AskGoodsMsgBO askGoodsMsgBO);
}
