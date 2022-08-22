package com.example.mall.mapper;

import com.example.mall.model.po.GoodsPO;
import com.example.mall.model.po.GoodsSpecPO;
import com.example.mall.model.po.GoodsTypePO;
import com.example.mall.model.po.MsgPO;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Classname AdminMapper
 * @Description
 * @Date 2022-08-19 17:52
 * @Created by Yang Yi-zhou
 */
public interface GoodsMapper {
    List<GoodsTypePO> selectAllGoodsTypeFromTableGoodsType();

    List<GoodsPO> selectGoodsFromTableGoodsByTypeId(@Param("typeId") Integer typeId);

    Integer selectStockNumFromTableGoodsSpecByGoodsId(@Param("goodsId") Integer goodsId);

    Integer insertIntoTableGoodsType(@Param("name") String name);

    GoodsTypePO selectGoodsTypeFromTableGoodsTypeByName(@Param("name") String name);

    Integer insertGoodsIntoTableGoods(@Param("goodsPO") GoodsPO goodsPO);

    Integer insertGoodsSpecListIntoTableGoodsSpec(@Param("specPOList") List<GoodsSpecPO> specPOList);

    Double selectPriceFromTableGoodsById(@Param("id") Integer id);

    Integer updateTableGoodsSetPriceById(@Param("price") Double price, @Param("id") Integer id);

    GoodsPO selectGoodsById(@Param("id") int id);

    List<GoodsSpecPO> selectGoodsSpecListByGoodsId(@Param("id") int id);

    Integer deleteGoodsById(@Param("id") Integer id);

    Integer deleteGoodsSpecByGoodsId(@Param("goodsId") Integer goodsId);

    Double selectSpecPriceFromGoodsSpecById(@Param("specId") Integer specId);

    List<MsgPO> selectNoReplyMsgList();

    List<MsgPO> selectRepliedMsgMsgList();

    Integer updateReplyContentAndStateById(@Param("id") Integer id, @Param("replyContent") String replyContent, @Param("replyTime") Timestamp replyTime);

    List<GoodsPO> selectGoodsListByKeyword(@Param("keyword") String keyword);

    List<MsgPO> selectMsgListByGoodsId(@Param("goodsId") Integer goodsId);

    Integer insertMsgUseMsgPO(@Param("msgPO") MsgPO msgPO);
}
