package com.example.mall.mapper;

import com.example.mall.model.po.OrderPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname OrderMapper
 * @Description
 * @Date 2022-08-21 15:20
 * @Created by Yang Yi-zhou
 */
public interface OrderMapper {
    List<OrderPO> selectOrderListByOrderPOWithPage(@Param("orderPO") OrderPO orderPO, @Param("moneyLimitLow") String moneyLimitLow, @Param("moneyLimitHigh") String moneyLimitHigh, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    Integer selectOrderTotalNumberByOrderPOWithPage(@Param("orderPO") OrderPO orderPO, @Param("moneyLimitLow") String moneyLimitLow, @Param("moneyLimitHigh") String moneyLimitHigh);

    OrderPO selectOrderById(@Param("id") Integer id);

    Integer updateOrderByOrderPO(@Param("orderPO") OrderPO orderPO);

    Integer deleteOrderById(@Param("id") Integer id);
}
