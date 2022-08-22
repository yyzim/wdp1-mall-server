package com.example.mall.mapper;

import com.example.mall.model.po.OrderPO;
import com.example.mall.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Classname TestOrderMapper
 * @Description
 * @Date 2022-08-21 15:54
 * @Created by Yang Yi-zhou
 */
public class TestOrderMapper {

    @Test
    public void testInsertOrderPO(){
        SqlSession session = MybatisUtils.openSession();
        OrderMapper mapper = session.getMapper(OrderMapper.class);


        OrderPO orderPO = new OrderPO();
        orderPO.setUserId(1002);
        mapper.insertOrderPO(orderPO);

        System.out.println(orderPO.getId());
    }


    @Test
    public void TestSelectOrderListByOrderPOWithPage() {
        OrderMapper mapper = MybatisUtils.openSession().getMapper(OrderMapper.class);
//        List<OrderPO> orderPOList = mapper.selectOrderListByOrderPOWithPage(new OrderPO(), null, null, 0, 3);

//        System.out.println("orderPOList = " + orderPOList);
        Integer integer = mapper.selectOrderTotalNumberByOrderPOWithPage(new OrderPO(), null, null);
        System.out.println("integer = " + integer);
    }
}
