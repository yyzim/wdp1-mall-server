package com.example.mall.mapper;

import com.example.mall.model.po.GoodsPO;
import com.example.mall.model.po.GoodsSpecPO;
import com.example.mall.model.po.GoodsTypePO;
import org.apache.ibatis.annotations.Param;

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
}
